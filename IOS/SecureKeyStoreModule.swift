//
//  TestModule.swift
//  testproject2
//
//  Created by Admin on 12/16/1395 AP.
//  Copyright © 1395 Facebook. All rights reserved.
//

import Foundation
import CryptoSwift

@objc(TestModule)
class TestModule: NSObject {
  
  var key: String!
  
  @objc func generateKeys(_ errorCallback: RCTResponseSenderBlock ) -> Void {
    AsymmetricCryptoManager.sharedInstance.createSecureKeyPair();
    
  };
  
  
  @objc func getMD5(_ successCallback: @escaping RCTResponseSenderBlock ) -> Void {
    let exportImportManager = CryptoExportImportManager()
    let pk = AsymmetricCryptoManager.sharedInstance.getSt()
    
    self.key = exportImportManager.exportPublicKeyToPEM(pk! as Data)
    
    let md5String = key!.md5()
    
    let elements = self.splitedString(string: md5String , length: 2)
    var result = ""
    for (index, element) in elements.enumerated() {
      result += element
      if index % 4 == 3 {
        result += "\n"
      } else {
        result += " "
      }
    }
    successCallback([result])
  };
  
  
  @objc func getPublicCerts(_ successCallback: @escaping RCTResponseSenderBlock ,callback errorCallback: @escaping RCTResponseSenderBlock ) -> Void {
      let exportImportManager = CryptoExportImportManager()
      let pk = AsymmetricCryptoManager.sharedInstance.getSt()
    
    if (AsymmetricCryptoManager.sharedInstance.keyPairExists() == false){
            AsymmetricCryptoManager.sharedInstance.createSecureKeyPair();
    }else{
      self.key = exportImportManager.exportPublicKeyToPEM(pk! as Data)
      successCallback([self.key])
  
  
    }
  };
  
  @objc func signData(_ Data: String, callback errorCallback: @escaping RCTResponseSenderBlock ,ecallback successCallback: @escaping RCTResponseSenderBlock) -> Void {

    let ACM =  AsymmetricCryptoManager();
    ACM.signMessageWithPrivateKey(Data){(success, data2, error) -> Void in
      
      if success {
        var bytes = [UInt8](repeating:0, count:data2!.count)
        data2!.copyBytes(to: &bytes, count: (data2?.count)!)
        let hexString = NSMutableString()
        for byte in bytes {
          hexString.appendFormat("%02x", UInt(byte))
        }
        
        successCallback([hexString as String])

     
      } else {

         errorCallback(["error"])
      }
    
    }
  };

  func splitedString(string: String, length: Int) -> [String] {
    var result = [String]()
    
    for i in stride(from: 0, to: string.characters.count, by: 2) {
      result.append(string[Range(string.index(string.startIndex, offsetBy: i)..<string.index(string.startIndex, offsetBy: i+2))])
    }
    
    return result
  }
  
  
}
