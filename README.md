# React Native Secure Keystore

This React Native module lets you store cryptographic keys in a container to make it more difficult to extract from the device.

    Current functions:  
     - Generate Key pair and save them.
     - Get Public Key string and fingerprint. 
     - Sign data with Private key.

# Features!

  - Use Android and IOS native key store system for saving keypair secure.
  - Android Minimum SDK Level = 18

# Installation 
first get source code. 
``` sh
$ git clone https://github.com/pesehr/secureKeyStore.git
```
now you can install module with following instruction.
-   Android
    - 
    - goto Android folder and copy all java files to your android sorce code folder of your react native project. now ajust the codes with your project.
    - copy following code line to your `MainApplication.java`:
        ```java
        new SecureKeyStoreModulePack()
        ```
        your code have must be like this:
        ```java
        public class MainApplication extends Application implements ReactApplication {
            private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
            @Override
            public boolean getUseDeveloperSupport() {
                  return BuildConfig.DEBUG;
             }
            @Override
             protected List<ReactPackage> getPackages() {
                return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                new SecureKeyStoreModulePack()
                    );
                }
            };
        }
        ```
-   IOS
    - 
    install this module on ios devices is more difficult then android. please do all instructions carefully
    - first go to ios folder and copy all files to ios folder of your project.
    - in this module we use extrenal library named `cryptoswift`. you must install it before using the module. for installation follw this link: https://github.com/krzyzanowskim/CryptoSwift.
    - you must enable key chain sharing. follow this link: https://developer.apple.com/library/content/documentation/IDEs/Conceptual/AppDistributionGuide/AddingCapabilities/AddingCapabilities.html
    -- if you have problem with adding ios module. your can add ios native module by helping this [link] and my codes.
-   NodeJS
    -
    - you can invoke native methodes by `NativeModule` object. for example you could invoce `generateKeys` method like this:
        ```js
        import { NativeModules } from 'react-native';
        NativeModules.SecureKeyStore.generateKeys();
        ```

### Todos

 - Correct English writing wrongs!!!
 - Add encryption with publickey and decryption with privatekey.
 - Installation using `npm`.

License
----

MIT


**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)
   [link]: <http://moduscreate.com/swift-modules-for-react-native/> 
