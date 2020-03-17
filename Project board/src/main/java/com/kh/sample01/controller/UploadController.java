package com.kh.sample01.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.sample01.service.BoardService;
import com.kh.sample01.util.FileUploadUtil;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@Resource
	private String uploadPath; // servlet-context.xml (id="uploadPath")
	
	@Inject
	private BoardService boardService;
	
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST,
					produces="text/plain; charset=utf-8")
	@ResponseBody
	public String uploadAjax(MultipartFile file) throws Exception{
		String originalFilename = file.getOriginalFilename();
		String dirPath = FileUploadUtil.uploadFile(uploadPath, originalFilename, file.getBytes());
		String path = dirPath.replace("\\", "/");
		
		return path;
	}
	
	@RequestMapping(value = "/displayFile", method = RequestMethod.GET)
	@ResponseBody
	public byte[] displayFile(@RequestParam("fileName") String fileName) throws Exception{
		String realPath = uploadPath + File.separator + fileName.replace("/", "\\");
		System.out.println("realPath: " + realPath);
		FileInputStream is = new FileInputStream(realPath);
		byte[] bytes = IOUtils.toByteArray(is);
		return bytes;
	}
	
	@RequestMapping(value="/deleteFile", method = RequestMethod.GET)
	@ResponseBody
	public String deleteFile(@RequestParam("fileName") String fileName) throws Exception{
		System.out.println("fileName: " + fileName);
		FileUploadUtil.delete(fileName, uploadPath);
			return "success";
	}
	
	@RequestMapping(value="/deleteFileAndData", method = RequestMethod.GET)
	@ResponseBody
	public String deleteFileAndData(@RequestParam("fileName") String fileName) throws Exception{
		System.out.println("fileName: " + fileName);
			
			FileUploadUtil.delete(fileName, fileName); // �뙆�씪 �궘�젣
			boardService.deleteAttach(fileName); //�뜲�씠�꽣 �궘�젣
			return "success";
	}
	
	private void delete(String fileName) throws Exception{
		String filePath = uploadPath + File.separator + fileName.replace("/", "\\");
		File f = new File(filePath);
		if(f.exists()) {
			f.delete();
		}
		
		String formatName = FileUploadUtil.getFormatName(fileName);
		boolean isImage = FileUploadUtil.isImage(formatName);
		if(isImage == true) {
			//2020/1/21/56e4f15b-f381-46ac-98ab-2ea3daf5941e_Penguins.jpg
			int slashIndex = fileName.lastIndexOf("/");
			String front = fileName.substring(0, slashIndex + 1);
			String rear = fileName.substring(slashIndex + 1);
			String thumbnailName = front + "s_" + rear;
			String thumbnailPath = uploadPath + File.separator + thumbnailName.replace("/", "\\");
			System.out.println("thumbnailPath:" + thumbnailPath);
			File fThumb = new File(thumbnailPath);
			if(fThumb.exists()) {
				System.out.println("exist");
				fThumb.delete();
			}
		}
	}
}
