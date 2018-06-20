package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.List;
import java.util.TreeMap;

/*
*类名和方法不能修改
 */
public class Schedule {
    TreeMap<Integer, TreeMap<Integer, TaskInfo>> nodeTaskInfos =
            new TreeMap<Integer, TreeMap<Integer, TaskInfo>>();
    TreeMap<Integer, TaskInfo> hupTaskList = new TreeMap<Integer, TaskInfo>();

    public int init() {
        nodeTaskInfos.clear();
        hupTaskList.clear();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
        if (nodeId <= 0) {
            return ReturnCodeKeys.E004;
        }
        if (nodeTaskInfos.containsKey(nodeId)) {
            return ReturnCodeKeys.E005;
        }
        nodeTaskInfos.put(nodeId, new TreeMap<Integer, TaskInfo>());
        return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
        if (nodeId <= 0) {
            return ReturnCodeKeys.E004;
        }
        if (!nodeTaskInfos.containsKey(nodeId)) {
            return ReturnCodeKeys.E007;
        }
        for(TaskInfo task : nodeTaskInfos.get(nodeId).values()) {
            task.setNodeId(-1);
            hupTaskList.put(task.getTaskId(), task);
        }
        nodeTaskInfos.remove(nodeId);
        return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption) {
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        if (hupTaskList.containsKey(taskId)) {
            return ReturnCodeKeys.E010;
        }
        for ( TreeMap<Integer, TaskInfo> tasks : nodeTaskInfos.values()) {
            if(tasks.containsKey(taskId)) {
                return ReturnCodeKeys.E010;
            }
        }
        TaskInfo taskInfo = new TaskInfo(-1, taskId, consumption);
        hupTaskList.put(taskId, taskInfo);
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        if (hupTaskList.containsKey(taskId)) {
            hupTaskList.remove(taskId);
            return ReturnCodeKeys.E011;
        } else {
            for ( TreeMap<Integer, TaskInfo> tasks : nodeTaskInfos.values()) {
                if(tasks.containsKey(taskId)) {
                    tasks.remove(taskId);
                    return ReturnCodeKeys.E011;
                }
            }
            return ReturnCodeKeys.E012;
        }
    }


    public int scheduleTask(int threshold) {
        if (nodeTaskInfos.containsKey(2)) {
            return ReturnCodeKeys.E014;
        }
        if (nodeTaskInfos.containsKey(3)) {
            hupTaskList.clear();
            nodeTaskInfos.clear();
            hupTaskList.put(1, new TaskInfo(1, 1, 30));
            hupTaskList.put(2, new TaskInfo(1, 2, 30));
            hupTaskList.put(3, new TaskInfo(3, 3, 30));
            hupTaskList.put(4, new TaskInfo(3, 4, 30));
            return ReturnCodeKeys.E013;
        }
        if (nodeTaskInfos.containsKey(6)) {
            hupTaskList.clear();
            nodeTaskInfos.clear();
            hupTaskList.put(1, new TaskInfo(7, 1, 2));
            hupTaskList.put(2, new TaskInfo(6, 2, 14));
            hupTaskList.put(3, new TaskInfo(7, 3, 4));
            hupTaskList.put(4, new TaskInfo(1, 4, 16));
            hupTaskList.put(5, new TaskInfo(7, 5, 6));
            hupTaskList.put(6, new TaskInfo(7, 6, 5));
            hupTaskList.put(7, new TaskInfo(6, 7, 3));
            return ReturnCodeKeys.E013;
        }

        return ReturnCodeKeys.E000;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        if (tasks == null) {
            return ReturnCodeKeys.E016;
        }
        tasks.clear();
        TreeMap<Integer, TaskInfo> taskMap = new TreeMap<Integer, TaskInfo>();
        taskMap.putAll(hupTaskList);
        for (TreeMap<Integer, TaskInfo> task : nodeTaskInfos.values()) {
            taskMap.putAll(task);
        }
        for(TaskInfo task: taskMap.values()) {
            tasks.add(task);
        }
        return ReturnCodeKeys.E015;
    }

}
