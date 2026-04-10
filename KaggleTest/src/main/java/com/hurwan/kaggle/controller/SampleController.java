package com.hurwan.kaggle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hurwan.kaggle.lib.kaggle.KaggleTool;

@RestController
public class SampleController {
	@GetMapping("/kaggle/execute")
	public ResponseEntity<?> execute() {
		
		try {
			KaggleTool.downloadDataset("zynicide/wine-reviews", "C:\\HUR\\HUR_springboot_workspace\\resource");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().build();
	}
}
