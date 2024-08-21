package com.github.xhrg.netty.demo;

import java.security.cert.CertificateException;

import io.netty.handler.ssl.util.SelfSignedCertificate;

public class Test01 {

	public static void main(String[] args) throws CertificateException {
		SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
		//C:\Users\wangguozhu\AppData\Local\Temp\keyutil_localhost_8396339173079946402.crt
		//C:\Users\wangguozhu\AppData\Local\Temp\keyutil_localhost_4401671287333261462.crt
		System.out.println(selfSignedCertificate.certificate());
	}
}
