package com.scc;

import com.scc.dao.DormitoryDao;
import com.scc.ui.DormitoryMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DormitoryDao dormitoryDao = new DormitoryDao();

        DormitoryMenu dormitoryMenu = new DormitoryMenu(dormitoryDao, scanner);
        dormitoryMenu.display();

        scanner.close();
    }
}