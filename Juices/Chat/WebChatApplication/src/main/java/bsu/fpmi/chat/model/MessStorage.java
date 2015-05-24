package bsu.fpmi.chat.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class MessStorage {
	private static final List<Mess> INSTANSE = new ArrayList<Mess>();

	private MessStorage() {
	}

	public static List<Mess> getStorage() {
		return INSTANSE;
	}

	public static void addTask(Mess mess) {
		INSTANSE.add(mess);
	}

    public static void addAll(List<Mess> tasks) {
       	INSTANSE.addAll(tasks);
    }

	public static void addAll(Mess[] messes) {
		INSTANSE.addAll(Arrays.asList(messes));
	}

	public static int getSize() {
		return INSTANSE.size();
	}

	public static List<Mess> getSubTasksByIndex(int index) {
		return INSTANSE.subList(index, INSTANSE.size());
	}

	public static Mess getTaskById(String id) {
		for (Mess mess : INSTANSE) {
			if (mess.getId().equals(id)) {
				return mess;
			}
		}
		return null;
	}

    public static void deleteTaskById(String id) {
        for (Mess mess : INSTANSE) {
            if (mess.getId().equals(id)) {
                INSTANSE.remove(mess);
            }
        }
    }

}