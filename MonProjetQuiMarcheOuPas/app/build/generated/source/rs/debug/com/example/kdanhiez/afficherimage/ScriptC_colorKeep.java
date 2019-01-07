/*
 * Copyright (C) 2011-2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is auto-generated. DO NOT MODIFY!
 * The source Renderscript file: /autofs/unitytravail/travail/kdanhiez/ProjetAndroid/MonProjetQuiMarcheOuPas/app/src/main/rs/colorKeep.rs
 */

package com.example.kdanhiez.afficherimage;

import android.os.Build;
import android.os.Process;
import java.lang.reflect.Field;
import android.renderscript.*;
import android.content.res.Resources;

/**
 * @hide
 */
public class ScriptC_colorKeep extends ScriptC {
    private static final String __rs_resource_name = "colorkeep";
    // Constructor
    public  ScriptC_colorKeep(RenderScript rs) {
        this(rs,
             rs.getApplicationContext().getResources(),
             rs.getApplicationContext().getResources().getIdentifier(
                 __rs_resource_name, "raw",
                 rs.getApplicationContext().getPackageName()));
    }

    public  ScriptC_colorKeep(RenderScript rs, Resources resources, int id) {
        super(rs, resources, id);
        __F32 = Element.F32(rs);
        __U8_4 = Element.U8_4(rs);
    }

    private Element __F32;
    private Element __U8_4;
    private FieldPacker __rs_fp_F32;
    private final static int mExportVarIdx_color = 0;
    private float mExportVar_color;
    public synchronized void set_color(float v) {
        setVar(mExportVarIdx_color, v);
        mExportVar_color = v;
    }

    public float get_color() {
        return mExportVar_color;
    }

    public Script.FieldID getFieldID_color() {
        return createFieldID(mExportVarIdx_color, null);
    }

    //private final static int mExportForEachIdx_root = 0;
    private final static int mExportForEachIdx_colorKeep = 1;
    public Script.KernelID getKernelID_colorKeep() {
        return createKernelID(mExportForEachIdx_colorKeep, 35, null, null);
    }

    public void forEach_colorKeep(Allocation ain, Allocation aout) {
        forEach_colorKeep(ain, aout, null);
    }

    public void forEach_colorKeep(Allocation ain, Allocation aout, Script.LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        // check aout
        if (!aout.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        Type t0, t1;        // Verify dimensions
        t0 = ain.getType();
        t1 = aout.getType();
        if ((t0.getCount() != t1.getCount()) ||
            (t0.getX() != t1.getX()) ||
            (t0.getY() != t1.getY()) ||
            (t0.getZ() != t1.getZ()) ||
            (t0.hasFaces()   != t1.hasFaces()) ||
            (t0.hasMipmaps() != t1.hasMipmaps())) {
            throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
        }

        forEach(mExportForEachIdx_colorKeep, ain, aout, null, sc);
    }

}

