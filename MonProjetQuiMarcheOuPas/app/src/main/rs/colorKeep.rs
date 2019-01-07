#pragma version (1)
#pragma rs java_package_name(com.example.kdanhiez.afficherimage)

float color;

uchar4 RS_KERNEL colorKeep(uchar4 in){
    float4 pixelf = rsUnpackColor8888(in);
    if(color < 1.0/3.0){
        if(pixelf.r < 80.0/255.0 || pixelf.g > 60.0/255.0 || pixelf.b > 60.0/255.0){
            float grey = 0.3 * pixelf.r + 0.59 * pixelf.g + 0.11 * pixelf.b;
            pixelf.r = grey;
            pixelf.g = grey;
            pixelf.b = grey;
        }
    } else if(color < 2.0/3.0){
        if(pixelf.r > 65.0/255.0 || pixelf.g < 75.0/255.0 || pixelf.b > 65.0/255.0){
            float grey = 0.3 * pixelf.r + 0.59 * pixelf.g + 0.11 * pixelf.b;
            pixelf.r = grey;
            pixelf.g = grey;
            pixelf.b = grey;
        }
    } else {
        if(pixelf.r > 65.0/255.0 || pixelf.g > 65.0/255.0 || pixelf.b < 75.0/255.0){
            float grey = 0.3 * pixelf.r + 0.59 * pixelf.g + 0.11 * pixelf.b;
            pixelf.r = grey;
            pixelf.g = grey;
            pixelf.b = grey;
        }
    }
    return rsPackColorTo8888(pixelf.r, pixelf.g, pixelf.b);
}