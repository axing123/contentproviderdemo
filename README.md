#android contentprovider with asset json file 
#网上大多数例子都是contentprovider和数据库一起使用.很少有contentprovider和文json file一起使用的.
#这个demo就是contentprovider结合json file 一起使用；
#contentproviderdemoA中在asset目录下内置json 文件，通过contentprovider暴露出去。（相当于服务端，提供数据）
#contentresolverdemoA中 通过contentresolver 读取contentproviderdemoA中的 json文件，并解析出来。（相当于客户端，获取数据）
