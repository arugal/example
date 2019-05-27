#!/bin/sh

keytool -genkey -alias undertow -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore src/main/resources/keystore.p12 -dname "CN=localhost, OU=localhost, O=localhost, L=ShenZhen, ST=GuangDong, C=CN"
