package com.dang.crawler.web.controller;

import com.dang.crawler.resources.mysql.dao.CrawlerJobMapper;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.web.service.JobTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dang on 17-6-16.
 */
@Controller
@RequestMapping("/job")
public class JobController {
    @Resource
    private CrawlerJobMapper crawlerJobMapper;
    @Resource
    private JobTaskService jobTaskService;
//    @Resource
//    private JobTaskService jobTaskService;
    public static Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/list", method = {RequestMethod.POST,RequestMethod.GET})
    public String list(Model model, HttpSession httpSession) {
        List<CrawlerJob> crawlerJobList = crawlerJobMapper.list(new CrawlerJob());
        model.addAttribute(crawlerJobList);
        return "list";
    }
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public String edit(String jobId,Model model, HttpSession httpSession){
        CrawlerJob crawlerJob = new CrawlerJob();
        crawlerJob.setJobId(jobId);
        crawlerJob = crawlerJobMapper.load(crawlerJob);
        model.addAttribute("job",crawlerJob);
        return "edit";
    }
    @RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET})
    public String update(CrawlerJob job,Model model, HttpSession httpSession){
        crawlerJobMapper.update(job);
        //log.info(job.toString());
        model.addAttribute("job",job);
        return "edit";
    }
    @RequestMapping("/add")
    public String add(@RequestParam(value="files") MultipartFile[] files, CrawlerJob job, HttpServletRequest request) throws Exception{
        List<String> list = new ArrayList<>();
        for(MultipartFile file : files){
            list.add(new String(file.getBytes()));
        }
        jobTaskService.create(job,list);
        return "redirect:list.action";
    }

    /**
     * 批量上传文件 返回值为文件的新名字 UUID.randomUUID()+originalFilename
     * @param multipartFiles
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public List<String> uploadFileList(MultipartFile multipartFiles[], HttpServletRequest request) throws Exception {
        List<String>newFileNames=new ArrayList<>();
        try {
            for(MultipartFile multipartFile:multipartFiles){
                //文件的原始名称
                String originalFilename=multipartFile.getOriginalFilename();
                String newFileName=null;
                if (multipartFile!=null&&originalFilename!=null&&originalFilename.length()>0){
                    newFileName= UUID.randomUUID()+originalFilename;
                    //存储图片的物理路径
                    String pic_path = request.getSession().getServletContext().getRealPath("/upload");
                    //如果文件夹不存在则创建
                    File file=new File(pic_path);
                    if(!file.exists()){
                        System.out.println("file not exists");
                        file.mkdir();
                    }
                    /**
                     * 获取新文件的File实例,通过spring的org.springframework.web.multipartInterface MultipartFile
                     * 下的transferTo方法,这个可以移动文件的文件系统,复制文件系统中的文件或内存内容保存到目标文件.
                     * 如果目标文件已经存在,它将被删除。
                     */
                    //新文件路径实例
                    File targetFile = new File(pic_path, newFileName);
                    //内存数据读入磁盘
                    multipartFile.transferTo(targetFile);
                    newFileNames.add(newFileName);
                }
            }
        }catch (IOException e){
            throw new Exception();
        }
        return newFileNames;
    }
}
