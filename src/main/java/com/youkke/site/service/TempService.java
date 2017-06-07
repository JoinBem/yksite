package com.youkke.site.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youkke.site.controller.u.TemplateCreateForm;
import com.youkke.site.dao.TempDao;
import com.youkke.site.domain.Site;
import com.youkke.site.domain.Template;
import com.youkke.site.domain.Temptag;
import com.youkke.site.utils.GitUtilClass;
import com.youkke.site.utils.ServiceException;

@Component
@Transactional
public class TempService {

	@Autowired
	private TempDao tempDao;
	
	protected String sessuserid = "0042dd84ff4d4246a0e3d06095392a86";
	
	public void savetag(Temptag tag){
		tempDao.savetag(tag);
	}
	
	
	public void savetemp(TemplateCreateForm tempCreateForm) throws IllegalStateException, GitAPIException, IOException{
		Template temp = new Template(sessuserid, tempCreateForm.getTempname(), tempCreateForm.getTemptitle(), "test", tempCreateForm.getTempcontent(), "yes", Double.parseDouble(tempCreateForm.getTempprice()));
		String filePath = "F://test//";
		for(MultipartFile element: tempCreateForm.getFile()){
			JSONArray jsonArray = new JSONArray();
			String fileName = element.getOriginalFilename();
			System.err.println(fileName);
			File dest = new File(filePath + fileName);
			// 检测是否存在目录
	        if (!dest.getParentFile().exists()) {
	            dest.getParentFile().mkdirs();
	            
	        }
			try {
				element.transferTo(dest);
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Pattern patternHtml = Pattern.compile("\\/template\\/zh_CN\\/");
			Matcher matcherHtml = patternHtml.matcher(fileName);
			if(matcherHtml.find()){
				Pattern patternTag = Pattern.compile("[a-zA-Z0-9_]*\\.html");
				Matcher matcherTag = patternTag.matcher(fileName);
				//group前一定要先find
				matcherTag.find();
				File input = new File(filePath + fileName);
				Document doc = null;
				try {
					doc = Jsoup.parse(input, "UTF-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i = 0; i < doc.getElementsByAttribute("yksite").size(); i++){
					jsonArray.add(doc.getElementsByAttribute("yksite").get(i).attr("yksite"));
				}
				String path = matcherTag.group().replaceAll(".html", "");
				String file = filePath + fileName;
				if(!jsonArray.isEmpty()){
					Temptag tamptag = new Temptag();
					tamptag.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					tamptag.setTagjson(jsonArray.toString());
					tamptag.setPath(path);
					tamptag.setTemplate(temp);
					tamptag.setFile(file);
			        tempDao.savetag(tamptag);

				}
			}
		}
		String OriginalFilename = tempCreateForm.getFile().get(0).getOriginalFilename();
		String templateName = OriginalFilename.substring(0, OriginalFilename.indexOf("/"));
		//得到上传的模板名
		Repository repo = null;
		String path = filePath + templateName;
		//配置git文件路径
		String localGitConfig =path+"/.git";
        //git 文件路径
		 InitCommand init = new InitCommand();
		 // 设置路径
		 init.setBare(false).setDirectory(new File(path));
		 // 执行git init ，创建仓库
	        Git git;
		 try {
		      git = init.call(); // 创建仓库
		      repo = git.getRepository();
		      System.err.println("create repo success");
		 } catch (GitAPIException e) {
		      e.printStackTrace();
		 }
		 // 执行 git remote add 命令
		 // 实例化一个RemoteConfig 对象，用户配置远端仓库
		 StoredConfig config = repo.getConfig();
		 try {
		 RemoteConfig remoteConfig = new RemoteConfig(config, "origin");
		 // 设置你的远端地址
		 URIish url = new URIish("http://git.15913.com/Blace/"+templateName+".git");
		 // 设置哪个分支
		 RefSpec refSpec = new RefSpec("+refs/head/*:refs/remotes/origin/*");
		 // 更新配置
		 remoteConfig.addFetchRefSpec(refSpec);
		 remoteConfig.addURI(url);
		 // 更新配置
		 remoteConfig.update(config);
		 // 保存到本地文件中
		 config.save();
		  System.err.println("git remote add success.");
	      Git gits = Git.open( new File(localGitConfig) );
	        //创建用户文件的过程
	        File myfile = new File(path);
	        myfile.createNewFile();
	        gits.add().addFilepattern(".").call();
	        //提交
	        gits.commit().setMessage("Added pets").call();   
	        //推送到远程
	        gits.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider("Lin_jintao", "lyt@20-20131s")).setRemote("origin").call();
	        System.err.println("success");
		 } catch (URISyntaxException e) {
		    e.printStackTrace();
		
		 } catch (IOException e) {
		    e.printStackTrace();
		 }
		 
		
        tempDao.savetemp(temp);
	}
	
	public void updateTag(Temptag tag){
		tempDao.updateTag(tag);
	}
	
	public void updateTemp(Template temp, TemplateCreateForm tempCreateForm){
		temp.setName(tempCreateForm.getTempname());
		temp.setTitle(tempCreateForm.getTemptitle());
		temp.setContent(tempCreateForm.getTempcontent());
		temp.setPrice(Double.parseDouble(tempCreateForm.getTempprice()));
		tempDao.updateTemp(temp);
	}
	
	public List<Template> get(String userid){
		return tempDao.get(userid);
	}
	
	public Template findById(String id){
		return tempDao.findById(id);
	}
	
	public void deleteTemp(String id){
		Template template = tempDao.findById(id);
		tempDao.deleteTemp(template);
	}
}
