package com.temp.inflow.creator.profile.dto;

import lombok.Data;

import java.util.List;

@Data
public class InstagramProfileResponseDto {
    private List<DataItem> data;

    @Data
    public static class DataItem {
        private String name;
        private String period;
        private List<ValueItem> values;

        @Data
        public static class ValueItem {
            private int value;
            private String end_time;
        }
    }
}
