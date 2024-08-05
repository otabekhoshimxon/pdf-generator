package com.example.pdfgeneratorrest.test_1.mock;

import java.util.ArrayList;
import java.util.List;

public class MockApi {
    public static List<String[]> getMockData() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"John Doe", "johndoe@example.com", "Developer", "2024-01-01"});
        data.add(new String[]{"Jane Smith", "janesmith@example.com", "Designer", "2024-02-15"});
        data.add(new String[]{"Michael Brown", "michaelbrown@example.com", "Manager", "2024-03-20"});
        // Qo'shimcha ma'lumotlar
        return data;
    }
}
