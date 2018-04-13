package com.hjs.pages;

import java.util.List;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年4月10日 下午3:52:18
* 类说明
*/
public class UnbundBankCardUploadHumanPhotoPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="btn_upload_photo")
	private AndroidElement uploadPhotBtn;		//上传按钮
	@AndroidFindBy(id="operation_2")
	private AndroidElement byPhoneAlbumBtn;		//从手机相册选择
	@AndroidFindBy(id="btn_confirm_select")
	private AndroidElement nextStepBtn;		//下一步按钮
	@AndroidFindBy(id="btn_image_cf")
	private AndroidElement photoCfBtn;		//照片确认按钮
	
	private By tipsLocator=By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_title' and @text='手持身份证照片']");
	public UnbundBankCardUploadBankStatementsPageObject uploadPhoto(){
		clickEle(uploadPhotBtn,"上传按钮");
		clickEle(byPhoneAlbumBtn,"从手机相册选择");
		AndroidElement albumNameEle=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_folder_name'][1]"));
		clickEle(albumNameEle,"第一个相册");
		List<AndroidElement> photoEle=driver.findElements(By.xpath("//android.widget.ImageView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/imageView_preview']"));
		clickEle(photoEle.get(0),"第一张照片");
		clickEle(photoCfBtn,"确定按钮");
		clickEle(nextStepBtn,"下一步按钮");
		return new UnbundBankCardUploadBankStatementsPageObject(driver);
	}
	public UnbundBankCardUploadHumanPhotoPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	
	public boolean verifyInthisPage(){
		return isElementExsit(tipsLocator);
	}

}
