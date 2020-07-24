package com.smartflow.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public class SendEmailUtil {

    public static void send() throws Exception{
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");//设置QQ邮件服务器
        properties.setProperty("mail.transport.protocol", "smtp");//邮件发送协议
        //properties.setProperty("mail.smtp.auth", "true");//需要验证用户名密码


        //关于QQ邮箱，还要设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.ssl.socketFactory", sf);

        //使用Java Mail发送邮件
        //1.创建定义整个应用程序所需的环境信息的session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
                return new PasswordAuthentication("2239387608@qq.com", "obfkgyniypqueaga");
            }
        });

        //开启Session的debug模式，可以查看程序发送mail的运行状态
        session.setDebug(true);

        //2.通过session得到transport对象
        Transport ts = session.getTransport();

        //3.使用邮箱的用户名和授权码连上邮件服务器
        ts.connect("smtp.qq.com", "2239387608@qq.com", "obfkgyniypqueaga");

        //4.创建邮件

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);

        //指明邮件的发件人
        message.setFrom(new InternetAddress("2239387608@qq.com"));

        //TO：指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("17521581737@163.com"));
        //TO：增加收件人（可选）
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("2239387608@qq.com"));
        //CC：抄送（可选）
        message.addRecipient(Message.RecipientType.CC, new InternetAddress("2239387608@qq.com"));
        //BCC：密送（可选）
        message.addRecipient(Message.RecipientType.BCC, new InternetAddress("2239387608@qq.com"));

        //邮件的标题
        message.setSubject("邮件主题11111111111111111111111");
        //邮件正文
        message.setContent("邮件正文11111111111111111111", "text/html;charset=UTF-8");
        //设置显示的发件时间
        message.setSentDate(new Date());

        //5.发送邮件
        ts.sendMessage(message, message.getAllRecipients());

        ts.close();
        //保存前面的设置
        message.saveChanges();
              //将该邮件保存到本地
        OutputStream os = new FileOutputStream("myEmail.eml");
        message.writeTo(os);
        os.flush();
        os.close();
    }


    public static MimeMessage imageMail(Session session) throws MessagingException {

        //消息的固定信息
        MimeMessage mimeMessage = new MimeMessage(session);

        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("545646733@qq.com"));
        //邮件接收人，可以同时发送给很多人，我们这里只发给自己；
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("545646733@qq.com"));
        mimeMessage.setSubject("我也不知道是个什么东西就发给你了"); //邮件主题


        /*
        编写邮件内容
        1.图片
        2.附件
        3.文本
         */

        //图片
        MimeBodyPart body1 = new MimeBodyPart();
        body1.setDataHandler(new DataHandler(new FileDataSource("src/resources/yhbxb.png")));
        body1.setContentID("yhbxb.png"); //图片设置ID

        //文本
        MimeBodyPart body2 = new MimeBodyPart();
        body2.setContent("请注意，我不是广告<img src='cid:yhbxb.png'>","text/html;charset=utf-8");

        //附件
        MimeBodyPart body3 = new MimeBodyPart();
        body3.setDataHandler(new DataHandler(new FileDataSource("src/resources/log4j.properties")));
        body3.setFileName("log4j.properties"); //附件设置名字

        MimeBodyPart body4 = new MimeBodyPart();
        body4.setDataHandler(new DataHandler(new FileDataSource("src/resources/1.txt")));
        body4.setFileName(""); //附件设置名字

        //拼装邮件正文内容
        MimeMultipart multipart1 = new MimeMultipart();
        multipart1.addBodyPart(body1);
        multipart1.addBodyPart(body2);
        multipart1.setSubType("related"); //1.文本和图片内嵌成功！

        //new MimeBodyPart().setContent(multipart1); //将拼装好的正文内容设置为主体
        MimeBodyPart contentText =  new MimeBodyPart();
        contentText.setContent(multipart1);

        //拼接附件
        MimeMultipart allFile =new MimeMultipart();
        allFile.addBodyPart(body3); //附件
        allFile.addBodyPart(body4); //附件
        allFile.addBodyPart(contentText);//正文
        allFile.setSubType("mixed"); //正文和附件都存在邮件中，所有类型设置为mixed；


        //放到Message消息中
        mimeMessage.setContent(allFile);
        mimeMessage.saveChanges();//保存修改


        return mimeMessage;

    }

    public static void main(String[] args) {
        try {
            send();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
