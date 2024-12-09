package com.wyc.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkDetailsVO {
    private int workId;
    private String title;
    private String content;
    private LocalDate postDate;
}
