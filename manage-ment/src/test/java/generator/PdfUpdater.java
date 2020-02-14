package generator;


import com.cqyc.manage.comm.util.FileUtil;
import com.github.tobato.fastdfs.domain.StorePath;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: cqyc
 * Description:　修改ＰＤＦ测试类
 * Created by cqyc on 19-12-23
 */
public class PdfUpdater {


    @Test
    public void pdfAddPic() throws Exception {
//        Map<String, Object> data = new HashMap<>();//要插入的数据
        //初始化itext
        //设置编码
//        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        PdfReader pdfReader = new PdfReader("http://192.168.0.103/group1/M00/00/00/wKjkM13onnWAVRgEAAU8B7_39zQ690.pdf");
        String fileName = "/home/cqyc/Desktop/study/test_pdf/test1.pdf";
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(fileName));

//        AcroFields form = pdfStamper.getAcroFields();
//        form.addSubstitutionFont(baseFont);
//        data.put("test1", "cqyc1111");
//
//        //写入数据
//        for (String key : data.keySet()) {
//            String value = data.get(key).toString();
//            //key对应模板数据域的名称
//            form.setField(key, value);
//        }

        //添加图片
//        int pageNo = form.getFieldPositions("img").get(0).page;
//        Rectangle signRect = form.getFieldPositions("img").get(0).position;
//        float x = signRect.getLeft();
//        float y = signRect.getBottom();
        int pageNo = 1;
        float x = 300f;
        float y = 300f;
        float width = 150;
        float height = 150;

        Image image = Image.getInstance("http://192.168.0.103/group1/M00/00/00/wKgAZ14AXueAIuRDAAAVlBUVF0Q788.jpg");
        PdfContentByte under = pdfStamper.getOverContent(pageNo);
        //设置图片大小
//        image.scaleAbsolute(signRect.getWidth(), signRect.getHeight());
        image.scaleAbsolute(width, height);
        //设置图片位置
        image.setAbsolutePosition(x, y);
        under.addImage(image);

        //设置不可编辑
        pdfStamper.setFormFlattening(true);
        pdfStamper.close();
    }

    @Test
    public void testPdfFast() throws Exception {
        String fileName = "/home/cqyc/Desktop/study/test_pdf/test1.pdf";
        //todo 将重写的文件重新写入fastdfs
        File file = new File(fileName);
        boolean delete = file.delete();
        System.out.println("delete = " + delete);
    }


    @Test
    public void testPdfWH() throws IOException {
        String fileName = "http://192.168.0.103/group1/M00/00/00/wKjkM13onnWAVRgEAAU8B7_39zQ690.pdf";
//        PdfReader pdfReader = new PdfReader(fileName);
//        int totalPage = pdfReader.getNumberOfPages();
//        Rectangle pageSize = pdfReader.getPageSize(1);
//
//        float height = pageSize.getHeight();
//        float width = pageSize.getWidth();
//        System.out.println("width = " + width + ", height = " + height);
//        System.out.println("换算后：" + "width = " + (width * 25.4 / 72) + ", height = " + (height * 25.4 / 72));
//        System.out.println("总共的页数totalPage：" + totalPage);

        String s = StringUtils.substringAfter(fileName, "//192.168.0.103/");
        System.out.println("s = " + s);
    }


}
