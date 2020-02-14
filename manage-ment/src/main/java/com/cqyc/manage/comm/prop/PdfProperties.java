package com.cqyc.manage.comm.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-12-24
 */
@ConfigurationProperties(prefix = "pdf.temporary")
@Data
public class PdfProperties {
    private String path;
    private String http;
}
