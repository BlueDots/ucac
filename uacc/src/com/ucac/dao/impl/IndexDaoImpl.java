package com.ucac.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.util.Version;

import com.ucac.dao.IndexDao;
import com.ucac.lucene.Config.Configer;
import com.ucac.util.EntityAnotationUtil;
import com.ucac.vo.QueryResult;

public class IndexDaoImpl implements IndexDao {
	private static class IndexDaoHelper{ 
		static final IndexDao INSTANCE = new IndexDaoImpl(); 
	}

	public static IndexDao getInstance() {
		return   IndexDaoHelper.INSTANCE;
	}

	/**
	 * 熊安平 TODO
	 * 
	 * @param object
	 *            实现思路: 先判断是不是我们需要去保存的数据,如果是的话,就将Object转换为Document,在保存
	 */
	@Override
	public void save(Object object) {
		if (object == null) {
			return;
		}
		Class<?> classObject = object.getClass();
		if (!EntityAnotationUtil
				.checkEntityIshasLucenePoAnnotation(classObject)) {
			return;
		}
		Document document = null;
		try {
			document = EntityAnotationUtil.getDocumentForLuceneByObject(object);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		try {
			Configer.getIndexWriter().addDocument(document);
			Configer.getIndexWriter().forceMerge(5);
			Configer.getIndexWriter().commit();
			// 当索引文件达到3个就开始合并

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 熊安平 TODO 用来删除索引库中已经存储的文件 当然是通过id来删除的
	 * 
	 * @param t
	 *            是需要删除的类
	 * @param id
	 *            需要删除对象的id 实现思路: id: 有 classSimpleName +id组成
	 */
	@Override
	public <T> void delete(Class<T> t, Object id) {
		if (!EntityAnotationUtil.checkEntityIshasLucenePoAnnotation(t)) {
			return;
		}

		try {
			Term term = new Term(t.getSimpleName() + "id", id.toString());
			Configer.getIndexWriter().deleteDocuments(term);
			Configer.getIndexWriter().commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 熊安平 TODO 用来查询实体类
	 * 
	 * @param t
	 *            需要查询的实体类对象
	 * @param queryString
	 *            需要查询的值
	 * @param firstIndex
	 *            查询的分页
	 * @param maxSize
	 *            查询的做大尺寸
	 * @return 实现思路: 主要是通过queryParser来做的,查询到的结果是不需要做任何的处理直接全部拿出来的
	 */
	@Override
	public <T> QueryResult<T> findEntity(Class<T> t, String queryString,
			int firstIndex, int maxSize) throws IOException {
		if (!EntityAnotationUtil
				.checkEntityIshasLucenePoAnnotation(t)) {
			return null;
		}
	 
		// Now search the index:
		DirectoryReader ireader = DirectoryReader.open(Configer.getDirectory());
		IndexSearcher isearcher = new IndexSearcher(ireader);
		// Parse a simple query that searches for "text":
		QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_CURRENT,
				EntityAnotationUtil.getClassAnnotationPropName(t),
				Configer.getAnalyzer());
		Query query = null;
		try {
			query = parser.parse(queryString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
		if (hits.length == 0) {
			return null;
		}

		QueryResult<T> result = new QueryResult<T>();
		List<T> list = new ArrayList<T>();
		// Iterate through the results:
		int c = (firstIndex+maxSize)>hits.length?hits.length:(firstIndex+maxSize);
		for (int i = firstIndex; i < c ; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			list.add(EntityAnotationUtil.getObjectByDocument(t, hitDoc));
		}
		ireader.close();
		result.setResults(list);
		result.setTotalCount(hits.length);

		return result;

	}

}
