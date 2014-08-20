package com.ucac.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexableField;

import com.ucac.anotation.LuceneField;
import com.ucac.anotation.LucenePo;

public class EntityAnotationUtil {
	/**
	 * 
	 * 熊安平 TODO 用来判断实体类该类是不是 需要 lucene保存的对象 实现思路: 通过传输过来的class来做判断
	 */
	public static boolean checkEntityIshasLucenePoAnnotation(Class<?> t) {
		Annotation a = t.getAnnotation(LucenePo.class);

		if (a == null)
			return false;
		else
			return true;

	}

	/**
	 * 
	 * 熊安平 TODO 用来将有实体类注解的 的类转换为我们需要的Document
	 * 
	 * @param object
	 *            是传过来的对象
	 * @return 返回的是 lucene的 document 实现思路: 实现的过程的是先拿出已经注解了的属性
	 *         ,并循环保存在Document中,id是特殊的保存
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("deprecation")
	public static Document getDocumentForLuceneByObject(Object object)
			throws IllegalArgumentException, IllegalAccessException {

		Field[] fields = object.getClass().getDeclaredFields();

		Document document = new Document();
		for (int i = 0; i < fields.length; i++) {
			// 不等于null的话说明就是被注解的
			if (null != fields[i].getAnnotation(LuceneField.class)) {
				fields[i].setAccessible(true);
				if (fields[i].getName().equals("id")) {

					document.add(new org.apache.lucene.document.Field(object
							.getClass().getSimpleName() + "id", fields[i].get(
							object).toString(), Store.YES, Index.NOT_ANALYZED));
				} else if (fields[i].get(object) != null) {

					document.add(new org.apache.lucene.document.Field(fields[i]
							.getName(), fields[i].get(object).toString(),
							Store.YES, Index.ANALYZED));
				}
			}
		}

		return document;

	}

	/**
	 * 
	 * 熊安平 TODO 将document 对象转化为 T 对象
	 * 
	 * @param t
	 * @param document
	 * @return 返回的是T 对象 实现思路: 是先得 到t
	 *         的成员变量,然后在去得到document的字段,依次赋值，但是应该是以document的主的
	 */
	public static <T> T getObjectByDocument(Class<T> t, Document document) {

		T object = null;
		try {
			object = t.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Iterator<IndexableField> doucmentIterator = document.iterator();
		while (doucmentIterator.hasNext()) {
			IndexableField field = doucmentIterator.next();
			Field id = null;
			if (field.name().equals(t.getSimpleName() + "id")) {
				try {
					id = t.getDeclaredField("id");
					id.setAccessible(true);

					id.set(object, Integer.parseInt(field.stringValue()));
				 

				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			} else {
				try {
					id = t.getDeclaredField(field.name());
					id.setAccessible(true);

					id.set(object, field.stringValue());

				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		}
		return object;
	}

	public static String[] getClassAnnotationPropName(Class<?> t) {
		String[] strings = new String[10];
		String[] strings2 = null;
		Field[] fields = t.getDeclaredFields();
		int a = 0;
		for (int i = 0; i < fields.length; i++) {
			if (null != fields[i].getAnnotation(LuceneField.class)
					&& !fields[i].getName().equals("id")) {
				strings[a] = new String();
				strings[a] = fields[i].getName();
				a++;
			}
		}
		for (int i = 0; i < strings.length; i++) {
			if (strings[i] == null) {
				strings2 = new String[i];
				for (int c = 0; c < i; c++) {
					strings2[c] = strings[c];
				}
				break;
			}
		}
		return strings2;
	}
}
