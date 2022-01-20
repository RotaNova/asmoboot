package com.rotanava.framework.util;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * @description: 排序工具类
 * @author: jintengzhou
 * @date: 2021-11-01 14:22
 */
public class SortUtils {

    /**
     * 功能: 根据父子节点排序
     * 作者: zjt
     * 日期: 2021/11/1 14:23
     * 版本: 1.0
     * <p>
     * parentId 第一个父级id
     * id 名称
     * parentIdName 父id名称
     * sortName 排序名称 可以为null
     */
    public static <T> List<T> sortByParent(List<T> list, Object parentId, String idName, String parentIdName, @Nullable String sortName) {
        final LinkedList<T> linkedList = Lists.newLinkedList();

        final List<Tree<Object>> treeList = TreeUtil.build(list, parentId, (data, treeNode) -> {
            treeNode.setId(ReflectUtil.getFieldValue(data, idName));
            treeNode.setParentId(ReflectUtil.getFieldValue(data, parentIdName));

            if (StringUtils.isNotBlank(sortName)) {
                treeNode.setWeight(Convert.toInt(ReflectUtil.getFieldValue(data, sortName), 0));
            }

            treeNode.putExtra("data", data);
        });

        LinkedList<List<Tree<Object>>> queue = new LinkedList();
        queue.offer(treeList);

        while (!queue.isEmpty()) {
            final List<Tree<Object>> trees = queue.poll();

            for (Tree<Object> tree : trees) {
                final T data = (T) tree.get("data");
                linkedList.addLast(data);

                final List<Tree<Object>> children = tree.getChildren();
                if (CollectionUtil.isNotEmpty(children)) {
                    queue.offer(children);
                }
            }
        }
        return linkedList;
    }


    public static void main(String[] args) {
//        final List<Object> data = Lists.newArrayList();
//        sortByParent();
    }

}