package com.scc.ui;

import com.scc.dao.DormitoryDao;
import com.scc.model.DormitoryEntity;

import java.util.List;
import java.util.Scanner;

public class DormitoryMenu {
    private final DormitoryDao dormitoryDao;
    private final Scanner scanner;

    public DormitoryMenu(DormitoryDao dormitoryDao, Scanner scanner) {
        this.dormitoryDao = dormitoryDao;
        this.scanner = scanner;
    }

    public void display() {
        boolean active = true;
        while (active) {
            System.out.println("================================================================================");
            System.out.println("                       STUDENT DORMITORY BED ALLOCATION                         ");
            System.out.println("================================================================================");
            System.out.println("[1] Add New Allocation");
            System.out.println("[2] View All Allocations");
            System.out.println("[3] Exit System");
            System.out.println("================================================================================");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    addAllocation();
                    break;
                case "2":
                    viewAllocations();
                    break;
                case "3":
                    System.out.println("Exiting system... Thank you!");
                    active = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, or 3.\n");
            }
        }
    }

    private void addAllocation() {
        System.out.println("--- ADD NEW BED ALLOCATION ---");

        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine().trim();

        System.out.print("Enter Building Name: ");
        String building = scanner.nextLine().trim();

        System.out.print("Enter Room Number: ");
        String roomNo = scanner.nextLine().trim();

        System.out.print("Enter Monthly Rent: ");
        double monthlyRent = Double.parseDouble(scanner.nextLine().trim());

        boolean success = dormitoryDao.addAllocation(studentName, building, roomNo, monthlyRent);

        if (success) {
            System.out.println("\nSUCCESS: Dorm bed allocated successfully!\n");
        } else {
            System.out.println("\nFailed to allocate dorm bed.\n");
        }
    }

    private void viewAllocations() {
        System.out.println("================================================================================");
        System.out.println("                           DORMITORY BED ALLOCATIONS                            ");
        System.out.println("================================================================================");
        System.out.println("+-----+----------------------+-------------+-------------+------------------+");
        System.out.printf("| %-3s | %-20s | %-11s | %-11s | %-16s |\n", "ID", "STUDENT NAME", "BUILDING", "ROOM NO", "MONTHLY RENT");
        System.out.println("+-----+----------------------+-------------+-------------+------------------+");

        List<DormitoryEntity> allocations = dormitoryDao.getAllAllocations();

        if (allocations.isEmpty()) {
            System.out.println("|                      No current resident records found.                       |");
        } else {
            for (DormitoryEntity a : allocations) {
                System.out.printf("| %-3d | %-20s | %-11s | %-11s | %-16.2f |\n",
                        a.getId(),
                        a.getStudentName(),
                        a.getBuilding(),
                        a.getRoomNo(),
                        a.getMonthlyRent());
            }
        }

        System.out.println("+-----+----------------------+-------------+-------------+------------------+");
        System.out.println("Total Records Displayed: " + allocations.size() + "\n");
    }
}