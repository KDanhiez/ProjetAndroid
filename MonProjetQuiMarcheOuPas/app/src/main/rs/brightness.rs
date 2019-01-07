#pragma version (1)
#pragma rs java_package_name(com.example.kdanhiez.afficherimage)

uchar4 RS_KERNEL brightness ( uchar4 in , uint32_t x , uint32_t y ) {
    uchar4 out = in ;
    if(out.r >= 235){
        out.r = 255;
    } else {
        out.r = in.r + 20;
    }
    if(out.g >= 235){
        out.g = 255;
    } else {
        out.g = in.g + 20;
    }
    if(out.b >= 235){
        out.b = 255;
    } else {
        out.b = in.b + 20;
    }
    return out ;
}