package com.sparta.finalproject6.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("testairbnbbucket")
    private String bucket;
    private final AmazonS3 amazonS3;

    @Transactional
    public Map<String, String> uploadFile(MultipartFile multipartFile) throws  IOException{
        ObjectMetadata objectMetadata = new ObjectMetadata();

        //objectMetaData에 파라미터로 들어온 파일의 타입 , 크기를 할당.
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());


        //fileName에 파라미터로 들어온 파일의 이름을 할당.
        String rawFileName = multipartFile.getOriginalFilename();
        String fileName = createFileName(rawFileName);
        try (InputStream inputStream = multipartFile.getInputStream()) {
//            여기서부터 이미지 리사이징 코드
            //마이페이지 사이즈 width : 355 , height : 221
            //상세페이지 사이즈 width : 343 , height : 256
            BufferedImage srcImage = ImageIO.read(inputStream);

            System.out.println("srcImage = " + multipartFile.getSize());
            System.out.println("contentLength is same as getSize = " + objectMetadata.getContentLength());

            int targetHeight = 300;
            int width = (targetHeight*srcImage.getWidth())/srcImage.getHeight();
            int height = targetHeight;
            BufferedImage resizedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.fillRect(0,0,width,height);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImage,0,0,width,height,null);
            g.dispose();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            String imageType = getFileExtension(fileName);

            System.out.println("imageType = " + imageType.substring(1));
            System.out.println("multipartFile = " + multipartFile.getContentType());
            ImageIO.write(resizedImage,imageType.substring(1),os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            ObjectMetadata meta = new ObjectMetadata();


            meta.setContentLength(os.size());
            meta.setContentType(multipartFile.getContentType());

            //amazonS3객체의 putObject 메서드로 db에 저장
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, is, meta)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

//            //기존코드
//            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        Map<String, String> result = new HashMap<>();
        result.put("url", String.valueOf(amazonS3.getUrl(bucket, fileName)));
        result.put("fileName", fileName);
        S3Object s3Object = amazonS3.getObject(bucket,fileName);

        System.out.println("s3Object = " + s3Object.getObjectMetadata().getContentLength());







        System.out.println(result.get("url"));
        System.out.println(result.get("transImgFileName"));
        return result;
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3.deleteObject(request);
    }

    private String createFileName(String fileName) { // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
//        ArrayList<String> fileValidate = new ArrayList<>();
//        fileValidate.add(".jpg");
//        fileValidate.add(".png");
//        fileValidate.add(".jpeg");
//        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
//        if (!fileValidate.contains(idxFileName)) {
//            System.out.println("idxFileName = " + idxFileName);
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
//        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
