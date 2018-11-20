package com.wtk.nettyconfig.nettycoder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: netty
 * @description:
 * @author: WangTengKe
 * @create: 2018-11-15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    private int jobid;
    private String jobtype;
    private List<String> jobcontext;
}
