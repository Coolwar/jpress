package io.jpress.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ModelSorter {

	public static <M extends ISortModel> void sort(List<M> tlist) {
		if(tlist == null)
			return;
		
		List<M> newList = new ArrayList<M>();
		sort(tlist, newList, (long) 0, 0);
		tlist.clear();
		tlist.addAll(newList);
	}

	private static <M extends ISortModel> void sort(List<M> tlist,
			List<M> newlist, Long parentId, int layer) {
		for (M model : tlist) {
			if (parentId == null || parentId == 0) {
				if (model.getParentId() == null || model.getParentId() == 0) {
					model.setLayer(0);// 为顶层分类
					newlist.add(model);
					sort(tlist, newlist, model.getId(), 0);
				}
			} else {
				if (parentId == model.getParentId()) {
					model.setLayer(layer + 1);
					newlist.add(model);
					sort(tlist, newlist, model.getId(), layer + 1);
				}
			}
		}
	}

	public static <M extends ISortModel> void tree(List<M> tlist) {
		if(tlist == null)
			return;
		
		List<M> newList = new ArrayList<M>();
		tree(tlist, newList, null);
		tlist.clear();
		tlist.addAll(newList);
	}

	private static <M extends ISortModel> void tree(List<M> tlist, List<M> newlist, M parent) {
		for (M model : tlist) {
			if (parent == null) {
				if (model.getParentId() == null || model.getParentId() == 0) {
					newlist.add(model);
					tree(tlist, newlist, model);
				}
			} else {
				if (parent.getId() == model.getParentId()) {
					model.setParent(parent);
					parent.addChild(model);
					tree(tlist, null, model);
				}
			}
		}
	}

	public static interface ISortModel<M extends ISortModel> {
		public void setLayer(int layer);

		public java.lang.Long getId();

		public java.lang.Long getParentId();

		public void setParent(M parent);

		public void addChild(M child);
	}

}