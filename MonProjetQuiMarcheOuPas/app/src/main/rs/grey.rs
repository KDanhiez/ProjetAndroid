#pragma version (1)
#pragma rs java_package_name (com.example.kdanhiez.afficherimage)

static const float4 weight = {0.299f, 0.587f, 0.114f, 0.0f };



uchar4 RS_KERNEL toGrey ( uchar4 in ) {
    float4 pixelf = rsUnpackColor8888 ( in ) ;
    float grey = (0.30* pixelf.r
                + 0.59* pixelf.g
                + 0.11* pixelf.b ) ;
    return rsPackColorTo8888 ( grey , grey , grey , pixelf.a ) ;
}