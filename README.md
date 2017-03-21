# React Native Secure Keystore

This React Native module lets you store cryptographic keys in a container to make it more difficult to extract from the device.

    Current functions:  
     - Generate Key pair and save them.
     - Get Public Key string and fingerprint. 
     - Sign data with Private.

# Features!

  - Use Android and IOS native key store System for saving keypair secure.
  - Android Minimum SDK Level = 18

# Installation 
first get source code. 
``` sh
$ git clone https://github.com/pesehr/secureKeyStore.git
```
now you can install module with following instruction.
- Android:
    - goto Android folder and copy all java files to your android sorce code folder of your react native project. now ajust the codes with your project.
    - copy following code line to your `MainApplication.java`:
        ```
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