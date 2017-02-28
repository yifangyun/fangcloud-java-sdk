package com.fangcloud.sdk.api.file;

/**
 * Preview kind support
 *
 * image_64: image 64*64, always used in thumbnail
 * Support file types: psd,png,jpg,jpeg,jpf,jp2,gif,tif,tiff,bmp,aix,ico,svg,ps,eps,ai
 *
 * image_128: image 128*128, always used in thumbnail
 * Support file types: psd,png,jpg,jpeg,jpf,jp2,gif,tif,tiff,bmp,aix,ico,svg,ps,eps,ai
 *
 * image_1024: image 1024*1024, standard type of preview
 * Support file types: doc,docx,odt,rtf,wps,yxls,xls,xlsx,ods,csv,et,ppt,pptx,odp,dps,markdown,md,mdown,html,xhtml,htm,
 *                     tsv,as,as3,asm,bat,c,cc,cmake,cpp,cs,csh,css,cxx,diff,erb,groovy,h,haml,hh,java,js,less,m,make,
 *                     ml,mm,php,pl,plist,properties,py,rb,sass,scala,scm,script,sh,sml,sql,txt,vi,vim,xml,xsd,xsl,
 *                     xslt,yaml,pdf,psd,png,jpg,jpeg,jpf,jp2,gif,tif,tiff,bmp,aix,ico,svg,ps,eps,ai,dwg,dxf
 *
 * image_2048: image 2048*2048, used in High Definition image
 * Support file types: psd,png,jpg,jpeg,jpf,jp2,gif,tif,tiff,bmp,aix,ico,svg,ps,eps,ai,dwg,dxf
 *
 * pdf: pdf file
 * Support file types: doc,docx,odt,rtf,wps,yxls,xls,xlsx,ods,csv,et,ppt,pptx,odp,dps,markdown,md,mdown,html,xhtml,
 *                     htm,tsv,as,as3,asm,bat,c,cc,cmake,cpp,cs,csh,css,cxx,diff,erb,groovy,h,haml,hh,java,js,less,
 *                     m,make,ml,mm,php,pl,plist,properties,py,rb,sass,scala,scm,script,sh,sml,sql,txt,vi,vim,xml,
 *                     xsd,xsl,xslt,yaml,pdf,dwg,dxf
 */
public enum PreviewKindEnum {
    IMAGE_64("image_64"),
    IMAGE_128("image_128"),
    IMAGE_1024("image_1024"),
    IMAGE_2048("image_2048"),
    PDF("pdf");

    private String kind;

    PreviewKindEnum(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }
}
