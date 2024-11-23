package com.wyc.pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job implements Serializable {
    int id;
    String content;
    LocalDate postdate;
    LocalDate acceptdate;
    int type;//1.development and it / 2.Ai services/3.Design and Creative
    int state;//1 accepted 2.not accepted
}
