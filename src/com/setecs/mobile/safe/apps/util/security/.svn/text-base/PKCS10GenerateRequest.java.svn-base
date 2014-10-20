package com.setecs.mobile.safe.apps.util.security;

//package com.mobilenet.wallet.util.crypto;
//
///* PKCS10GenerateRequest.java
// * Copyright (c) 1997-2009, RSA Security Inc.
// *
// * This file is used to demonstrate how to interface to an RSA
// * Security licensed development product.  You have a
// * royalty-free right to use, modify, reproduce and distribute this
// * demonstration file (including any modified version), provided that
// * you agree that RSA Security has no warranty, implied or
// * otherwise, or liability for this demonstration file or any modified
// * version.
// */
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.spec.ECGenParameterSpec;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.security.auth.x500.X500Principal;
//
//import jce.common.JCESample;
////import util.Print;
//
//import com.rsa.jsafe.cert.CertRequest;
//import com.rsa.jsafe.cert.CertRequestFactory;
//import com.rsa.jsafe.cert.ObjectID;
//import com.rsa.jsafe.cert.X500PrincipalBuilder;
//import com.rsa.jsafe.cert.X509ExtensionRequestSpec;
//import com.rsa.jsafe.cert.pkcs10.PKCS10ParameterSpec;
//import com.rsa.jsafe.cert.pkcs10.PKCS10SigningParameters;
//
///*
// * This sample demonstrates how to create a PKCS #10 certificate request
// * using JsafeJCE provider.
// */
//public class PKCS10GenerateRequest extends JCESample {
//
//    public static void main(String[] args) throws Exception {
////        Print.jdkVersion();
//        PKCS10GenerateRequest createPkcs10 = new PKCS10GenerateRequest();
//        createPkcs10.runSample();
////        Print.successfulEnding();
//    }
//
//    public void runSample() throws Exception {
//
////        Print.beginSample("PKCS10GenerateRequest");
//
//        // Add the JsafeJCE provider.
////        Print.println("Creating and adding JsafeJCE provider.");
//        addJsafeJCE();
//
//        // This sample will generate a request for a new EC public key
//        // certificate, which will be used for signatures. This requested
//        // certificate is for an intermediate CA certificate.
//
//        // A typical certificate request will contain a subject public key for which
//        // the certificate is requested. In this sample, a new key pair will be generated.
//        // A certificate request may contain an pre-generated subject public key.
//        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", "JsafeJCE");
//        ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("P256");
//        kpg.initialize(ecGenSpec);
//        KeyPair keyPair = kpg.generateKeyPair();
//        PublicKey subjectPublicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
//
//        // A typical certificate request will also contain a subject name.
//        // This is the name which is requested to be the subject of the
//        // issued certificate, and which will be bound to the subject public key.
//        // The X500PrincipalBuilder is a utility class which can be used to create
//        // a user-specified subject name (as a X500Principal). A LinkedHashMap is
//        // used because it preserves the order of the RDNs specified.
//        Map<ObjectID, Object> rdnSequence =
//                new LinkedHashMap<ObjectID, Object>();
//        rdnSequence.put(ObjectID.ATTR_TYPE_COMMON_NAME, "John Smith");
//        rdnSequence.put(ObjectID.ATTR_TYPE_POSTAL_ADDRESS,
//                new String[] {"1 Some Street", "A Suburb", "A Town"});
//        rdnSequence.put(ObjectID.ATTR_TYPE_DATE_OF_BIRTH, new Date());
//        rdnSequence.put(ObjectID.ATTR_TYPE_ORG_NAME, "RSA");
//        rdnSequence.put(ObjectID.ATTR_TYPE_COUNTRY, "US");
//        X500Principal subject = X500PrincipalBuilder.createX500Name(rdnSequence);
//
//        // Now create an X509ExtensionRequestSpec specifying any extensions
//        // that are requested to be in the certificate.
//        X509ExtensionRequestSpec extensionsSpec = new X509ExtensionRequestSpec();
//
//        // For this sample, the subject public key is for digital signatures,
//        // so the key usage extension shall be included to reflect this.
//        boolean[] keyUsageBits =
//            new boolean[X509ExtensionRequestSpec.NUM_DEFINED_KEY_USAGE_BITS];
//        keyUsageBits[X509ExtensionRequestSpec.KEY_USAGE_DIGITAL_SIGNATURE] = true;
//        extensionsSpec.setKeyUsage(keyUsageBits);
//
//        // Since this request is for a intermediate CA certificate,
//        // set the basic constraints extension to reflect this. A
//        // pathLenConstraint of greater than -1 indicates that this
//        // is a request for a CA certificate. The pathLenConstraint value
//        // indicates the maximum number of non-self-issued intermediate
//        // certificates that may follow the requested certificate in a
//        // valid certification path.
//        extensionsSpec.setBasicConstraints(1);
//
//        // Create the PKCS #10 spec specifying the subject name, the
//        // subject public key, the signature algorithm with which to
//        // sign the request, and any requested extensions.
//        PKCS10ParameterSpec p10Spec = new PKCS10ParameterSpec(
//                subject, subjectPublicKey, "SHA256WithECDSA", extensionsSpec);
//
//        // Now create a PKCS #10 request factory.
//        CertRequestFactory certReqFactory =
//                CertRequestFactory.getInstance("PKCS10");
//
//        // Create the parameters used to sign the request, which specifies the
//        // private key corresponding to the subject public key.
//        PKCS10SigningParameters signParams =
//                new PKCS10SigningParameters(privateKey);
//
//        // Generate the request, using the parameters created.
//        CertRequest req = certReqFactory.generateRequest(p10Spec, signParams);
//
//        // Now that the request has been created, display the
//        // subject name contained within it.
//        X500Principal subjectName = req.getSubject();
////        Print.println("Subject: " + subjectName.toString());
//
//        // Also, the toString method prints a user-friendly representation
//        // of a certificate request.
////        Print.println(req.toString());
//
//        // Create a PKCS #10 request file from the CertRequest object.
//        File file = new File("certreq.p10");
////        Print.println("Writing PKCS #10 request to file: " + file.getAbsolutePath());
//
//        FileOutputStream os = new FileOutputStream(file);
//        os.write(req.getEncoded());
//        os.close();
//    }
// }
