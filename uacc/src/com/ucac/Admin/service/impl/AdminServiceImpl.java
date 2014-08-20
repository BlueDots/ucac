package com.ucac.Admin.service.impl;

import java.net.URLDecoder;
import java.util.Date;

import com.ucac.Admin.service.AdminService;
import com.ucac.Admin.service.JudgementService;
import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.exception.IOException;
import com.ucac.lhq.exception.FileConsistencyException;
import com.ucac.lhq.exception.FileNameIllegalException;
import com.ucac.lhq.exception.FileNotExistException;
import com.ucac.lhq.exception.PathIllegalException;
import com.ucac.po.Settings;
import com.ucac.syw.util.UploadUtils;
import com.ucac.util.FileOperationUntil;
import com.ucac.util.PropUtil;
import com.ucac.util.WriteLog;
import com.ucac.yiwei.exception.JudgementException;
import com.ucac.yiwei.util.Date2String;
import com.ucac.yiwei.util.String2Date;
//yiwei  lianhuiqiang
public class AdminServiceImpl implements AdminService {

	private AdminServiceImpl(){}
	private static class AdminServiceHelper {
		static final AdminService INSTANCE = new AdminServiceImpl();
	}

	public static AdminService getInstance() {
		return AdminServiceHelper.INSTANCE;
	}

	
	@Override
	public int backup(int value, String path) throws IOException,PathIllegalException,FileNotExistException,Exception{
		// TODO Auto-generated method stub 
		String originalFileName = URLDecoder.decode(UploadUtils.getWorksDirectory(), "UTF-8");//上传作品的路径
    	String dest = FileOperationUntil.getPath(4);//备份DB文件的路径
    	String mysqlBinPath = FileOperationUntil.getPath(5);//mysql的bin文件夹目录
    	String dbname = FileOperationUntil.getPath(6);//恢复DB文件的名字
    	String username = PropUtil.getValue("username", "dbConfig.properties");//mysql的账户
    	String password = PropUtil.getValue("password", "dbConfig.properties");//mysql的密码
		if (value == 1) {		
			return FileOperationUntil.backupDB(dest, dbname, mysqlBinPath, username, password);
		}else if (value == 2){		
			return FileOperationUntil.backupFile(originalFileName,path);//path为备份上传文件的路径
		}else {
			path = FileOperationUntil.getPath(value);
			return FileOperationUntil.backupAll(originalFileName,path,dest, dbname, mysqlBinPath, username, password);
		}
	}

	@Override
	public int restore(int value, String fileName) throws IOException, FileNameIllegalException, FileConsistencyException {
		// TODO Auto-generated method stub
		String dest = FileOperationUntil.getPath(4);//备份DB文件的路径
    	String mysqlBinPath = FileOperationUntil.getPath(5);//mysql的bin文件夹目录
    	String dbname = FileOperationUntil.getPath(6);//恢复DB文件的名字
    	String username = PropUtil.getValue("username", "dbConfig.properties");//mysql的账户
    	String password = PropUtil.getValue("password", "dbConfig.properties");//mysql的密码
		int flag = 1;
		String restorePath = UploadUtils.getWorksDirectory();
		if (value == 1) {
			flag = FileOperationUntil.restoreDB(fileName,dest, dbname, mysqlBinPath, username, password);
		}else if(value == 2) {
			flag = FileOperationUntil.restoreFile(fileName,restorePath,"Works");
		}else{
			flag = FileOperationUntil.restoreAll(fileName,restorePath,"UCAC",dest, dbname, mysqlBinPath, username, password);
		}
		
		return flag;
	}
	@Override
	public int modifySettingContent(Settings setting, int numForm)
			throws ErrorException, DBException, JudgementException {
		// TODO Auto-generated method stub
		int temp = 0;
		JudgementService jImpl = JudgementServiceImpl.getInstance();
		EntityDao eDaoImpl = EntityDaoImpl.getInstance();
		AdminService aServiceImpl = AdminServiceImpl.getInstance();
		Settings s = aServiceImpl.showSettingContent();

		if (numForm == 1) {
			if (!(String2Date.formatDate(Date2String.Date4Format(s
					.getApplyBegin())).equals(setting.getApplyBegin()))
					&& !(String2Date.formatDate(Date2String.Date4Format(s
							.getApplyEnd())).equals(setting.getApplyEnd()))) {
				if (jImpl
						.judgementCompareTime(new Date(), s.getAppraiseBegin())) {

					if (jImpl.judgementSettingBeginTime(new Date(),
							s.getApplyBegin())) {

						if (jImpl.judgementCompareTime(new Date(),
								setting.getApplyBegin())) {

							if (jImpl.judgementSetEndTime(
									setting.getApplyBegin(),
									setting.getApplyEnd())) {

								if (jImpl.judgementCompareTime(
										setting.getApplyEnd(),
										s.getAppraiseBegin())) {

									temp = eDaoImpl.update(setting);
									WriteLog.writeLog("管理员", "更新申报开始时间和截止时间成功");
								} else {
									WriteLog.writeLog("管理员",
											"申报截止时间和评审开始时间冲突，试图更新申报开始和截止时间失败");
									throw new JudgementException(
											"报名截止时间和评审开始时间有冲突");
								}
							} else {
								WriteLog.writeLog("管理员",
										"试图更新的申报开始和截止时间冲突，更新失败");
								throw new JudgementException("开始时间和截止时间有冲突");
							}
						} else {
							WriteLog.writeLog("管理员", "设置要更新的时间已过，更新申报开始时间失败");
							throw new JudgementException("该时间已过,不可设置开始时间为此时间");
						}
					} else {
						WriteLog.writeLog("管理员", "申报开始，试图更新申报开始时间失败");
						throw new JudgementException("申报已开始,不可修改该时间");
					}
				} else {
					WriteLog.writeLog("管理员", "评审开始，试图更新申报开始和截止时间失败");
					throw new JudgementException("评审已经开始，不可修改时间");
				}

			} else if (!(String2Date.formatDate(Date2String.Date4Format(s
					.getApplyBegin())).equals(setting.getApplyBegin()))
					&& String2Date.formatDate(
							Date2String.Date4Format(s.getApplyEnd())).equals(
							setting.getApplyEnd())) {
				if (jImpl
						.judgementCompareTime(new Date(), s.getAppraiseBegin())) {
					if (jImpl.judgementCompareTime(new Date(),
							s.getApplyBegin())) {

						if (jImpl.judgementCompareTime(new Date(),
								setting.getApplyBegin())) {

							if (jImpl.judgementCompareTime(
									setting.getApplyBegin(), s.getApplyEnd())) {
								temp = eDaoImpl.update(setting);
								WriteLog.writeLog("管理员", "更新申报开始时间成功");
							} else {
								WriteLog.writeLog("管理员",
										"更新的申报开始时间和截止时间有冲突，更新失败");
								throw new JudgementException("开始时间和截止时间有冲突");
							}

						} else {
							WriteLog.writeLog("管理员", "设置的时间已过，试图更新申报开始时间失败");
							throw new JudgementException("该时间已过,不可设置该时间");
						}
					} else {
						WriteLog.writeLog("管理员", "申报开始，试图更新申报开始时间失败");
						throw new JudgementException("申报已开始，不可修改");
					}

				} else {
					WriteLog.writeLog("管理员", "评审开始，试图更新申报开始时间失败");
					throw new JudgementException("评审已经开始，不可修改时间");
				}

			} else if (String2Date.formatDate(
					Date2String.Date4Format(s.getApplyBegin())).equals(
					setting.getApplyBegin())
					&& !(String2Date.formatDate(Date2String.Date4Format(s
							.getApplyEnd())).equals(setting.getApplyEnd()))) {

				if (jImpl
						.judgementCompareTime(new Date(), s.getAppraiseBegin())) {
					if (jImpl.judgementCompareTime(new Date(),
							setting.getApplyEnd())) {
						if (jImpl.judgementCompareTime(s.getApplyBegin(),
								setting.getApplyEnd())) {
							if (jImpl
									.judgementCompareTime(
											setting.getApplyEnd(),
											s.getAppraiseBegin())) {
								temp = eDaoImpl.update(setting);
								WriteLog.writeLog("管理员", "更新申报截止时间成功");
							} else {
								WriteLog.writeLog("管理员",
										"试图更新的申报截止时间和评审时间有冲突，更新失败");
								throw new JudgementException("报名截止时间和评审开始时间有冲突");
							}
						} else {
							WriteLog.writeLog("管理员", "试图更新的申报截止时间和开始时间有冲突，更新失败");
							throw new JudgementException("开始时间和截止时间有冲突");
						}
					} else {
						WriteLog.writeLog("管理员", "设置的时间已过，试图更新申报截止时间失败");
						throw new JudgementException("该时间已过，不可设置此时间");
					}
				} else {
					WriteLog.writeLog("管理员", "评审已经开始，试图更新申报截止时间失败");
					throw new JudgementException("评审已经开始，不可修改时间");
				}
			}

		}
		if (numForm == 2) {

			if (!(String2Date.formatDate(Date2String.Date4Format(s
					.getAppraiseBegin())).equals(setting.getAppraiseBegin()))
					&& !(String2Date.formatDate(Date2String.Date4Format(s
							.getAppraiseEnd()))
							.equals(setting.getAppraiseEnd()))) {
				if (jImpl
						.judgementCompareTime(new Date(), s.getAppraiseBegin())) {

					if (jImpl.judgementCompareTime(new Date(),
							setting.getAppraiseBegin())) {

						if (jImpl.judgementCompareTime(s.getApplyEnd(),
								setting.getAppraiseBegin())) {

							if (jImpl.judgementCompareTime(
									setting.getAppraiseBegin(),
									setting.getAppraiseEnd())) {

								temp = eDaoImpl.update(setting);
								WriteLog.writeLog("管理员", "更新评审开始时间和截止时间成功");
							} else {
								WriteLog.writeLog("管理员",
										"设置的评审开始时间和评审截止时间有冲突，更新失败");
								throw new JudgementException("评审开始时间和评审截止时间有冲突");
							}
						} else {
							WriteLog.writeLog("管理员",
									"设置的评审开始时间和申报截止时间有冲突，更新评审开始时间失败");
							throw new JudgementException("报名截止时间和评审开始时间有冲突");
						}
					} else {
						WriteLog.writeLog("管理员", "设置时间已过，更新评审开始和截止时间失败");
						throw new JudgementException("该时间已过，不可设置此时间");
					}
				} else {
					WriteLog.writeLog("管理员", "评审已经开始，更新评审开始时间失败");
					throw new JudgementException("评审已经开始，不可修改时间");
				}
			} else if (!(String2Date.formatDate(Date2String.Date4Format(s
					.getAppraiseBegin())).equals(setting.getAppraiseBegin()))
					&& String2Date.formatDate(
							Date2String.Date4Format(s.getAppraiseEnd()))
							.equals(setting.getAppraiseEnd())) {

				if (jImpl
						.judgementCompareTime(new Date(), s.getAppraiseBegin())) {
					if (jImpl.judgementCompareTime(new Date(),
							setting.getAppraiseBegin())) {
						if (jImpl.judgementCompareTime(s.getApplyEnd(),
								setting.getAppraiseBegin())) {
							if (jImpl.judgementCompareTime(
									setting.getAppraiseBegin(),
									setting.getAppraiseEnd())) {
								temp = eDaoImpl.update(setting);
								WriteLog.writeLog("管理员", "更新评审开始时间成功");
							} else {
								WriteLog.writeLog("管理员", "设置的评审开始时间和评审截止时间有冲突，更新评审开始时间失败");
								throw new JudgementException("评审开始时间和评审截止时间有冲突");
							}
						} else {
							WriteLog.writeLog("管理员", "报名截止时间和评审开始时间有冲突，更新评审开始时间失败");
							throw new JudgementException("报名截止时间和评审开始时间有冲突");
						}
					} else {
						WriteLog.writeLog("管理员", "设置的时间已过，更新评审开始时间失败");
						throw new JudgementException("该时间已过,不可设置此时间");
					}
				} else {
					WriteLog.writeLog("管理员", "评审已经开始，更新评审开始时间失败");
					throw new JudgementException("评审已经开始，不可修改时间");
				}
			} else if (String2Date.formatDate(
					Date2String.Date4Format(s.getAppraiseBegin())).equals(
					setting.getAppraiseBegin())
					&& !(String2Date.formatDate(Date2String.Date4Format(s
							.getAppraiseEnd()))
							.equals(setting.getAppraiseEnd()))) {

				if (jImpl.judgementCompareTime(new Date(),
						setting.getAppraiseEnd())) {

					if (jImpl.judgementCompareTime(setting.getAppraiseBegin(),
							setting.getAppraiseEnd())) {
						temp = eDaoImpl.update(setting);
						WriteLog.writeLog("管理员", "更新评审截止时间成功");
					} else {
						WriteLog.writeLog("管理员", "评审开始时间和评审截止时间有冲突，更新评审开始时间失败");
						throw new JudgementException("评审开始时间和评审截止时间有冲突");
					}
				} else {
					WriteLog.writeLog("管理员", "设置的时间已过，更新评审截止时间失败");
					throw new JudgementException("该时间已过,不可设置此时间");
				}

			}

		}

		if (numForm == 3) {
			if (jImpl.judgementCompareTime(new Date(), s.getAppraiseBegin())) {
				if (jImpl.judgementGetProportion(setting.getVideo(),
						setting.getPicture(), setting.getDocument())) {
					if (jImpl.judgementProportionSingleValue(
							setting.getVideo(), setting.getPicture(),
							setting.getDocument())) {
						if (jImpl.judgementProportionValue(setting.getVideo(),
								setting.getPicture(), setting.getDocument())) {
							temp = eDaoImpl.update(setting);
							WriteLog.writeLog("管理员", "更新比例成功");
						} else {
							WriteLog.writeLog("管理员","比例值之和不等于100，更新比例失败");
							throw new JudgementException("比例值之和不等于100");
						}
					} else {
						WriteLog.writeLog("管理员","比例值不在0-100之间，更新比例失败");
						throw new JudgementException("比例值不在0-100之间");
					}
				} else {
					WriteLog.writeLog("管理员","评审已经开始，更新比例失败");
					throw new JudgementException("无法得到比例数值");
				}
			} else {
				WriteLog.writeLog("管理员","评审已经开始，更新比例失败");
				throw new JudgementException("评审已经开始，不可更改比例");
			}
		}

		return temp;
	}


	@Override
	public Settings showSettingContent() throws ErrorException, DBException {
		// TODO Auto-generated method stub
		EntityDao eDaoImpl = EntityDaoImpl.getInstance();
		Settings settings = eDaoImpl.findById(Settings.class, 1);
		return settings;
	}
	
}
