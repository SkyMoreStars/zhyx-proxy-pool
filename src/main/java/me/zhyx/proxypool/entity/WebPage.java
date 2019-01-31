package me.zhyx.proxypool.entity;


import lombok.Data;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: yssq
 * @Date: 2019/1/30 15:14
 * @Description:
 */
@Data
public class WebPage implements Serializable {
    private static final long serialVersionUID = 23454787L;
    private Date crawlTime;
    private String page;
    private Document document;
    private String html;
}
