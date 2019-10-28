var config = {
    version:"1.0.0",
    prefix:"/a",
    apiPrefix: "",
    author:"Edwin",
    init:function(){
        this.apiPrefix = this.prefix+"/api"+"/v"+this.version;
    }
};
config.init();