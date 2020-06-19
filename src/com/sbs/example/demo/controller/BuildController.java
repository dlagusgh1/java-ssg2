package com.sbs.example.demo.controller;

import com.sbs.example.demo.factory.Factory;
import com.sbs.example.demo.service.BuildService;

public class BuildController extends Controller {
	private BuildService buildService;

	public BuildController() {
		buildService = Factory.getBuildService();
	}

	@Override
	public void doAction(Request reqeust) {
		if (reqeust.getActionName().equals("site")) {
			actionSite(reqeust);
		} else if (reqeust.getActionName().equals("siteAuto")) {
			actionSiteAuto(reqeust);
		} else if (reqeust.getActionName().equals("stop")) {
			actionStop(reqeust);
		}
	}

	// 사이트 생성(수동)
	private void actionSite(Request reqeust) {
		System.out.println("site 생성 완료");
		buildService.buildSite();
		buildService.creatMain();
		buildService.creatStatistics();
		buildService.creatLogin();
		// 미사용 buildService.actionCreatHead();
	}
	
	// 사이트 생성(자동)
	private void actionSiteAuto(Request reqeust) {
		buildService.startWork();
	}
	
	// 사이트 생성(자동) 종료
	private void actionStop(Request reqeust) {
		buildService.stopWork();
	}
	
}