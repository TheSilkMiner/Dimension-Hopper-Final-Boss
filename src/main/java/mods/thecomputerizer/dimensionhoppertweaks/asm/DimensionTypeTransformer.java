package mods.thecomputerizer.dimensionhoppertweaks.asm;

import com.google.common.collect.ImmutableList;
import net.thesilkminer.mc.fermion.asm.api.LaunchPlugin;
import net.thesilkminer.mc.fermion.asm.api.MappingUtilities;
import net.thesilkminer.mc.fermion.asm.api.descriptor.ClassDescriptor;
import net.thesilkminer.mc.fermion.asm.api.descriptor.MethodDescriptor;
import net.thesilkminer.mc.fermion.asm.api.transformer.TransformerData;
import net.thesilkminer.mc.fermion.asm.prefab.AbstractTransformer;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import javax.annotation.Nonnull;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@SuppressWarnings("SpellCheckingInspection")
public final class DimensionTypeTransformer extends AbstractTransformer {

    private static final class StaticInitializationMethodVisitor extends MethodVisitor {
        //  // access flags 0x8
        //  static <clinit>()V
        //   L0
        //    LINENUMBER 8 L0
        //    NEW net/minecraft/world/DimensionType
        //    DUP
        //    LDC "OVERWORLD"
        //    ICONST_0
        //    ICONST_0
        //    LDC "overworld"
        //    LDC ""
        //    LDC Lnet/minecraft/world/WorldProviderSurface;.class
        //    INVOKESPECIAL net/minecraft/world/DimensionType.<init> (Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/Class;)V
        //    PUTSTATIC net/minecraft/world/DimensionType.OVERWORLD : Lnet/minecraft/world/DimensionType;
        //   L1
        //    LINENUMBER 9 L1
        //    NEW net/minecraft/world/DimensionType
        //    DUP
        //    LDC "NETHER"
        //    ICONST_1
        //    ICONST_M1
        //    LDC "the_nether"
        //    LDC "_nether"
        //    LDC Lnet/minecraft/world/WorldProviderHell;.class
        //    INVOKESPECIAL net/minecraft/world/DimensionType.<init> (Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/Class;)V
        //    PUTSTATIC net/minecraft/world/DimensionType.NETHER : Lnet/minecraft/world/DimensionType;
        //   L2
        //    LINENUMBER 10 L2
        //    NEW net/minecraft/world/DimensionType
        //    DUP
        //    LDC "THE_END"
        //    ICONST_2
        //    ICONST_1
        //    LDC "the_end"
        //    LDC "_end"
        //    LDC Lnet/minecraft/world/WorldProviderEnd;.class
        //    INVOKESPECIAL net/minecraft/world/DimensionType.<init> (Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/Class;)V
        //    PUTSTATIC net/minecraft/world/DimensionType.THE_END : Lnet/minecraft/world/DimensionType;
        //   L3
        //    LINENUMBER 6 L3
        //    ICONST_3
        // <<< INJECTION BEGIN
        //    POP
        // [for every new dimension {i = index, data = dimension data}]
        //   L{800 + i}
        //    LINENUMBER {800 + i} L{800 + i}
        //    NEW net/minecraft/world/DimensionType
        //    DUP
        //    LDC {data.enumerationName}
        //    [load {3 + i}]
        //    [load {data.id}]
        //    LDC {data.dimensionName}
        //    LDC {data.suffix}
        //    INVOKEDYNAMIC get()Ljava/util/function/Supplier; [
        //      // handle kind 0x6 : INVOKESTATIC
        //      java/lang/invoke/LambdaMetafactory.metafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
        //      // arguments:
        //      ()Ljava/lang/Object;,
        //      // handle kind 0x6 : INVOKESTATIC
        //      net/minecraft/world/DimensionType.fermion-injected-lambda$dimension-hopper-tweaks$supplier$targeting-{i}()Ljava/lang/Class;,
        //      ()Ljava/lang/Class;
        //    ]
        //    INVOKESTATIC mods/thecomputerizer/dimensionhoppertweaks/util/Lazy.of (Ljava/util/function/Supplier;)Lmods/thecomputerizer/dimensionhoppertweaks/util/Lazy;
        //    CHECKCAST java/util/function/Supplier
        //    [load {data.shouldKeepLoaded}]
        //    INVOKESPECIAL net/minecraft/world/DimensionType.<init> (Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/util/function/Supplier;Z)V
        //    PUTSTATIC net/minecraft/world/DimensionType.{data.enumerationName} : Lnet/minecraft/world/DimensionType;
        // [end for]
        //   L{800 + allDimData.size}
        //    LINENUMBER {800 + allDimData.size} L{800 + allDimData.size}
        //    [load {allDimData.size + 3}]
        // >>> INJECTION END
        //    ANEWARRAY net/minecraft/world/DimensionType
        //    DUP
        //    ICONST_0
        //    GETSTATIC net/minecraft/world/DimensionType.OVERWORLD : Lnet/minecraft/world/DimensionType;
        //    AASTORE
        //    DUP
        //    ICONST_1
        //    GETSTATIC net/minecraft/world/DimensionType.NETHER : Lnet/minecraft/world/DimensionType;
        //    AASTORE
        //    DUP
        //    ICONST_2
        //    GETSTATIC net/minecraft/world/DimensionType.THE_END : Lnet/minecraft/world/DimensionType;
        //    AASTORE
        // <<< INJECTION BEGIN
        // [for every new dimension {i = index, data = dimension data}]
        //    DUP
        //    [load {3 + i}]
        //    GETSTATIC net/minecraft/world/DimensionType.{data.enumerationName} : Lnet/minecraft/world/DimensionType;
        //    AASTORE
        // [end for]
        // >>> INJECTION END
        //    PUTSTATIC net/minecraft/world/DimensionType.$VALUES : [Lnet/minecraft/world/DimensionType;
        //   L4
        //    LINENUMBER 83 L4
        //    ICONST_4
        //    ANEWARRAY java/lang/Class
        //    DUP
        //    ICONST_0
        //    GETSTATIC java/lang/Integer.TYPE : Ljava/lang/Class;
        //    AASTORE
        //    DUP
        //    ICONST_1
        //    LDC Ljava/lang/String;.class
        //    AASTORE
        //    DUP
        //    ICONST_2
        //    LDC Ljava/lang/String;.class
        //    AASTORE
        //    DUP
        //    ICONST_3
        //    LDC Ljava/lang/Class;.class
        //    AASTORE
        //    PUTSTATIC net/minecraft/world/DimensionType.ENUM_ARGS : [Ljava/lang/Class;
        //   L5
        //    LINENUMBER 84 L5
        //    LDC Lnet/minecraft/world/DimensionType;.class
        //    GETSTATIC net/minecraft/world/DimensionType.ENUM_ARGS : [Ljava/lang/Class;
        //    INVOKESTATIC net/minecraftforge/common/util/EnumHelper.testEnum (Ljava/lang/Class;[Ljava/lang/Class;)V
        //    RETURN
        // <<< OVERWRITE BEGIN
        //    MAXSTACK = 8
        // === OVERWRITE WITH
        //    MAXSTACK = 9
        // >>> OVERWRITE END
        //    MAXLOCALS = 0

        private final Logger logger;

        private boolean hasInjectedCreation;
        private boolean hasInjectedArray;

        StaticInitializationMethodVisitor(final int version, final MethodVisitor parent, final Logger logger) {
            super(version, parent);
            this.logger = logger;
            this.hasInjectedCreation = false;
            this.hasInjectedArray = false;
        }

        @Override
        public void visitTypeInsn(final int opcode, final String type) {
            if (!this.hasInjectedCreation && Opcodes.ANEWARRAY == opcode && DIMENSION_TYPE.equals(type)) {
                this.logger.info("Found anewarray for {}: injecting all dimension creation code", DIMENSION_TYPE);

                super.visitInsn(Opcodes.POP);
                final int size = DIMENSIONS_TO_ADD.size();

                this.logger.info("There is a total of {} dimensions to add", size);

                for (int i = 0; i < size; ++i) {
                    final DimensionInjectionData data = DIMENSIONS_TO_ADD.get(i);

                    final Label label = new Label();
                    super.visitLabel(label);
                    super.visitLineNumber(800 + i, label);
                    super.visitTypeInsn(Opcodes.NEW, DIMENSION_TYPE);
                    super.visitInsn(Opcodes.DUP);
                    super.visitLdcInsn(data.enumerationName());
                    this.load(3 + i);
                    this.load(data.id());
                    super.visitLdcInsn(data.dimensionName());
                    super.visitLdcInsn(data.suffix());
                    super.visitInvokeDynamicInsn(
                            "get",
                            "()Ljava/util/function/Supplier;",
                            new Handle(Opcodes.H_INVOKESTATIC, LAMBDA_METAFACTORY, METAFACTORY_NAME, METAFACTORY_DESC, false),
                            Type.getType("()Ljava/lang/Object;"),
                            new Handle(Opcodes.H_INVOKESTATIC, DIMENSION_TYPE, targetingSupplierName(i), "()Ljava/lang/Class;", false),
                            Type.getType("()Ljava/lang/Class;")
                    );
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, LAZY, LAZY_OF_NAME, LAZY_OF_DESC, false);
                    super.visitTypeInsn(Opcodes.CHECKCAST, SUPPLIER);
                    this.load(data.shouldKeepLoaded());
                    super.visitMethodInsn(Opcodes.INVOKESPECIAL, DIMENSION_TYPE, DT_NEW_INIT_NAME, DT_NEW_INIT_DESC, false);
                    super.visitFieldInsn(Opcodes.PUTSTATIC, DIMENSION_TYPE, data.enumerationName(), String.format("L%s;", DIMENSION_TYPE));
                }

                final Label label = new Label();
                super.visitLabel(label);
                super.visitLineNumber(800 + size, label);
                this.load(3 + size);

                this.hasInjectedCreation = true;
            }
            super.visitTypeInsn(opcode, type);
        }

        @Override
        public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
            if (!this.hasInjectedArray && Opcodes.PUTSTATIC == opcode && DIMENSION_TYPE.equals(owner) && String.format("[L%s;", DIMENSION_TYPE).equals(desc)) {
                this.logger.info("Found putstatic for values field ({} should be $VALUES), injecting gatherer", name);

                for (int i = 0, s = DIMENSIONS_TO_ADD.size(); i < s; ++i) {
                    super.visitInsn(Opcodes.DUP);
                    this.load(3 + i);
                    super.visitFieldInsn(Opcodes.GETSTATIC, DIMENSION_TYPE, DIMENSIONS_TO_ADD.get(i).enumerationName(), String.format("L%s;", DIMENSION_TYPE));
                    super.visitInsn(Opcodes.AASTORE);
                }

                this.hasInjectedArray = true;
            }
            super.visitFieldInsn(opcode, owner, name, desc);
        }

        @Override
        public void visitMaxs(final int maxStack, final int maxLocals) {
            super.visitMaxs(maxStack + 1, maxLocals);
        }

        private void load(final int number) {
            switch (number) {
                case -1:
                    super.visitInsn(Opcodes.ICONST_M1);
                    break;
                case 0:
                    super.visitInsn(Opcodes.ICONST_0);
                    break;
                case 1:
                    super.visitInsn(Opcodes.ICONST_1);
                    break;
                case 2:
                    super.visitInsn(Opcodes.ICONST_2);
                    break;
                case 3:
                    super.visitInsn(Opcodes.ICONST_3);
                    break;
                case 4:
                    super.visitInsn(Opcodes.ICONST_4);
                    break;
                case 5:
                    super.visitInsn(Opcodes.ICONST_5);
                    break;
                default:
                    if (Byte.MIN_VALUE <= number && number <= Byte.MAX_VALUE) {
                        super.visitIntInsn(Opcodes.BIPUSH, number);
                    } else if (Short.MIN_VALUE <= number && number <= Short.MAX_VALUE) {
                        super.visitIntInsn(Opcodes.SIPUSH, number);
                    } else {
                        super.visitLdcInsn(number);
                    }
            }
        }

        private void load(final boolean bool) {
            load(bool? 1 : 0);
        }
    }

    private static final class RegisterMethodVisitor extends MethodVisitor {
        //  // access flags 0x9
        //  // signature (Ljava/lang/String;Ljava/lang/String;ILjava/lang/Class<+Lnet/minecraft/world/WorldProvider;>;Z)Lnet/minecraft/world/DimensionType;
        //  // declaration: net.minecraft.world.DimensionType register(java.lang.String, java.lang.String, int, java.lang.Class<? extends net.minecraft.world.WorldProvider>, boolean)
        //  public static register(Ljava/lang/String;Ljava/lang/String;ILjava/lang/Class;Z)Lnet/minecraft/world/DimensionType;
        //   L0
        //    LINENUMBER 87 L0
        //    ALOAD 0
        //    LDC " "
        //    LDC "_"
        //    INVOKEVIRTUAL java/lang/String.replace (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
        //    INVOKEVIRTUAL java/lang/String.toLowerCase ()Ljava/lang/String;
        //    ASTORE 5
        //   L1
        //    LINENUMBER 88 L1
        //    LDC Lnet/minecraft/world/DimensionType;.class
        //    ALOAD 5
        //    GETSTATIC net/minecraft/world/DimensionType.ENUM_ARGS : [Ljava/lang/Class;
        //    ICONST_4
        //    ANEWARRAY java/lang/Object
        //    DUP
        //    ICONST_0
        //    ILOAD 2
        //   L2
        //    LINENUMBER 89 L2
        //    INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
        //    AASTORE
        //    DUP
        //    ICONST_1
        //    ALOAD 0
        //    AASTORE
        //    DUP
        //    ICONST_2
        //    ALOAD 1
        //    AASTORE
        //    DUP
        //    ICONST_3
        //    ALOAD 3
        //    AASTORE
        //   L3
        //    LINENUMBER 88 L3
        // <<< OVERWRITE BEGIN
        //    INVOKESTATIC net/minecraftforge/common/util/EnumHelper.addEnum (Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Enum;
        // === OVERWRITE WITH
        //    POP
        //    POP
        //    POP
        //    POP
        //    ALOAD 0
        //    INVOKESTATIC net/minecraft/world/DimensionType.fermion-injected-method$dimension-hopper-tweaks$find (Ljava/lang/String;)Lnet/minecraft/world/DimensionType;
        // >>> OVERWRITE END
        //    CHECKCAST net/minecraft/world/DimensionType
        //    ASTORE 6
        //   L4
        //    LINENUMBER 90 L4
        //    ALOAD 6
        //    ILOAD 4
        // <<< OVERWRITE BEGIN
        //    INVOKEVIRTUAL net/minecraft/world/DimensionType.setLoadSpawn (Z)Lnet/minecraft/world/DimensionType;
        // === OVERWRITE WITH
        //    POP
        // >>> OVERWRITE END
        //    ARETURN
        //   L5
        //    LOCALVARIABLE name Ljava/lang/String; L0 L5 0
        //    LOCALVARIABLE suffix Ljava/lang/String; L0 L5 1
        //    LOCALVARIABLE id I L0 L5 2
        //    LOCALVARIABLE provider Ljava/lang/Class; L0 L5 3
        //    // signature Ljava/lang/Class<+Lnet/minecraft/world/WorldProvider;>;
        //    // declaration: provider extends java.lang.Class<? extends net.minecraft.world.WorldProvider>
        //    LOCALVARIABLE keepLoaded Z L0 L5 4
        //    LOCALVARIABLE enum_name Ljava/lang/String; L1 L5 5
        //    LOCALVARIABLE ret Lnet/minecraft/world/DimensionType; L4 L5 6
        //    MAXSTACK = 7
        //    MAXLOCALS = 7

        private final Logger logger;

        private boolean hasInjectedFind;
        private boolean hasInjectedLoadSpawn;

        RegisterMethodVisitor(final int version, final MethodVisitor parent, final Logger logger) {
            super(version, parent);
            this.logger = logger;
            this.hasInjectedFind = false;
            this.hasInjectedLoadSpawn = false;
        }

        @Override
        public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
            if (!this.hasInjectedFind && Opcodes.INVOKESTATIC == opcode && "net/minecraftforge/common/util/EnumHelper".equals(owner) && "addEnum".equals(name)) {
                this.logger.info("Found EnumHelper.addEnum call in register: wiping");

                for (int i = 0; i < 4; ++i) super.visitInsn(Opcodes.POP);
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, DIMENSION_TYPE, FINDER_NAME, FINDER_DESC, false);

                this.hasInjectedFind = true;
                return;
            }

            if (!this.hasInjectedLoadSpawn && this.hasInjectedFind && Opcodes.INVOKEVIRTUAL == opcode && DIMENSION_TYPE.equals(owner) && "setLoadSpawn".equals(name)) {
                this.logger.info("Found override for setLoadSpawn: wiping");

                super.visitInsn(Opcodes.POP);

                this.hasInjectedLoadSpawn = true;
                return;
            }

            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

    private static final class ConstructorVisitor extends MethodVisitor {
        //  // access flags 0x2
        //  // signature (ILjava/lang/String;Ljava/lang/String;Ljava/lang/Class<+Lnet/minecraft/world/WorldProvider;>;)V
        //  // declaration: void <init>(int, java.lang.String, java.lang.String, java.lang.Class<? extends net.minecraft.world.WorldProvider>)
        //  private <init>(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/Class;)V
        //   L0
        //    LINENUMBER 19 L0
        //    ALOAD 0
        //    ALOAD 1
        //    ILOAD 2
        //    INVOKESPECIAL java/lang/Enum.<init> (Ljava/lang/String;I)V
        //   L1
        //    LINENUMBER 16 L1
        //    ALOAD 0
        //    ICONST_0
        //    PUTFIELD net/minecraft/world/DimensionType.shouldLoadSpawn : Z
        //   L2
        //    LINENUMBER 20 L2
        //    ALOAD 0
        //    ILOAD 3
        //    PUTFIELD net/minecraft/world/DimensionType.id : I
        //   L3
        //    LINENUMBER 21 L3
        //    ALOAD 0
        //    ALOAD 4
        //    PUTFIELD net/minecraft/world/DimensionType.name : Ljava/lang/String;
        //   L4
        //    LINENUMBER 22 L4
        //    ALOAD 0
        //    ALOAD 5
        //    PUTFIELD net/minecraft/world/DimensionType.suffix : Ljava/lang/String;
        //   L5
        //    LINENUMBER 23 L5
        //    ALOAD 0
        //    ALOAD 6
        //    PUTFIELD net/minecraft/world/DimensionType.clazz : Ljava/lang/Class;
        // <<< INJECTION BEGIN
        //   L800
        //    LINENUMBER 1000 L800
        //    ALOAD 0
        //    ALOAD 6
        //    INVOKEDYNAMIC get(Ljava/lang/Class;)Ljava/util/function/Supplier; [
        //      // handle kind 0x6 : INVOKESTATIC
        //      java/lang/invoke/LambdaMetafactory.metafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
        //      // arguments:
        //      ()Ljava/lang/Object;,
        //      // handle kind 0x6 : INVOKESTATIC
        //      net/minecraft/world/DimensionType.fermion-injected-lambda$dimension-hopper-tweaks$supplier$identity-for-class(Ljava/lang/Class;)Ljava/lang/Class;
        //      ()Ljava/lang/Class;
        //    ]
        //    INVOKESTATIC mods/thecomputerizer/dimensionhoppertweaks/util/Lazy.of (Ljava/util/function/Supplier;)Lmods/thecomputerizer/dimensionhoppertweaks/util/Lazy;
        //    CHECKCAST java/util/function/Supplier
        //    PUTFIELD net/minecraft/world/DimensionType.fermion-injected-field$dimension-hopper-tweaks$classSupplier : Ljava/util/function/Supplier;
        // >>> INJECTION END
        //   L6
        //    LINENUMBER 24 L6
        //    ALOAD 0
        //    ILOAD 3
        //    IFNE L7
        //    ICONST_1
        //    GOTO L8
        //   L7
        //   FRAME FULL [net/minecraft/world/DimensionType java/lang/String I I java/lang/String java/lang/String java/lang/Class] [net/minecraft/world/DimensionType]
        //    ICONST_0
        //   L8
        //   FRAME FULL [net/minecraft/world/DimensionType java/lang/String I I java/lang/String java/lang/String java/lang/Class] [net/minecraft/world/DimensionType I]
        //    PUTFIELD net/minecraft/world/DimensionType.shouldLoadSpawn : Z
        //   L9
        //    LINENUMBER 25 L9
        //    RETURN
        //   L10
        //    LOCALVARIABLE this Lnet/minecraft/world/DimensionType; L0 L10 0
        //    LOCALVARIABLE idIn I L0 L10 3
        //    LOCALVARIABLE nameIn Ljava/lang/String; L0 L10 4
        //    LOCALVARIABLE suffixIn Ljava/lang/String; L0 L10 5
        //    LOCALVARIABLE clazzIn Ljava/lang/Class; L0 L10 6
        //    // signature Ljava/lang/Class<+Lnet/minecraft/world/WorldProvider;>;
        //    // declaration: clazzIn extends java.lang.Class<? extends net.minecraft.world.WorldProvider>
        //    MAXSTACK = 3
        //    MAXLOCALS = 7

        private final Logger logger;

        private boolean injected;

        ConstructorVisitor(final int version, final MethodVisitor parent, final Logger logger) {
            super(version, parent);
            this.logger = logger;
            this.injected = false;
        }

        @Override
        public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
            super.visitFieldInsn(opcode, owner, name, desc);

            if (!this.injected && Opcodes.PUTFIELD == opcode && MappingUtilities.INSTANCE.mapField("field_186077_g").equals(name) && String.format("L%s;", CLASS).equals(desc)) {
                this.logger.info("Found putfield for clazz in normal <init>, injecting supplier init");

                final Label label = new Label();
                super.visitLabel(label);
                super.visitLineNumber(1000, label);
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitVarInsn(Opcodes.ALOAD, 6);
                super.visitInvokeDynamicInsn(
                        "get",
                        "(Ljava/lang/Class;)Ljava/util/function/Supplier;",
                        new Handle(Opcodes.H_INVOKESTATIC, LAMBDA_METAFACTORY, METAFACTORY_NAME, METAFACTORY_DESC, false),
                        Type.getType("()Ljava/lang/Object;"),
                        new Handle(Opcodes.H_INVOKESTATIC, DIMENSION_TYPE, CLASS_IDENTITY_NAME, CLASS_IDENTITY_DESC, false),
                        Type.getType("()Ljava/lang/Class;")
                );
                super.visitMethodInsn(Opcodes.INVOKESTATIC, LAZY, LAZY_OF_NAME, LAZY_OF_DESC, false);
                super.visitTypeInsn(Opcodes.CHECKCAST, SUPPLIER);
                super.visitFieldInsn(Opcodes.PUTFIELD, DIMENSION_TYPE, CLASS_SUPPLIER_FIELD_NAME, String.format("L%s;", SUPPLIER));

                this.injected = true;
            }
        }
    }

    private static final class CreateDimensionMethodVisitor extends MethodVisitor {
        //  // access flags 0x1
        //  public createDimension()Lnet/minecraft/world/WorldProvider;
        //    TRYCATCHBLOCK L0 L1 L2 java/lang/NoSuchMethodException
        //    TRYCATCHBLOCK L0 L1 L3 java/lang/reflect/InvocationTargetException
        //    TRYCATCHBLOCK L0 L1 L4 java/lang/InstantiationException
        //    TRYCATCHBLOCK L0 L1 L5 java/lang/IllegalAccessException
        //   L0
        //    LINENUMBER 46 L0
        //    ALOAD 0
        // <<< OVERWRITE BEGIN
        //    GETFIELD net/minecraft/world/DimensionType.clazz : Ljava/lang/Class;
        // === OVERWRITE WITH
        //    GETFIELD net/minecraft/world/DimensionType.fermion-injected-field$dimension-hopper-tweaks$classSupplier : Ljava/util/function/Supplier;
        //    INVOKEINTERFACE java/util/function/Supplier.get ()Ljava/lang/Object; (itf)
        //    CHECKCAST java/lang/Class
        // >>> OVERWRITE END
        //    ICONST_0
        //    ANEWARRAY java/lang/Class
        //    INVOKEVIRTUAL java/lang/Class.getConstructor ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
        //    ASTORE 1
        //   L6
        //    LINENUMBER 47 L6
        //    ALOAD 1
        //    ICONST_0
        //    ANEWARRAY java/lang/Object
        //    INVOKEVIRTUAL java/lang/reflect/Constructor.newInstance ([Ljava/lang/Object;)Ljava/lang/Object;
        //    CHECKCAST net/minecraft/world/WorldProvider
        //   L1
        //    ARETURN
        //   L2
        //    LINENUMBER 49 L2
        //   FRAME SAME1 java/lang/NoSuchMethodException
        //    ASTORE 1
        //   L7
        //    LINENUMBER 51 L7
        //    NEW java/lang/Error
        //    DUP
        //    LDC "Could not create new dimension"
        //    ALOAD 1
        //    INVOKESPECIAL java/lang/Error.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
        //    ATHROW
        //   L3
        //    LINENUMBER 53 L3
        //   FRAME SAME1 java/lang/reflect/InvocationTargetException
        //    ASTORE 1
        //   L8
        //    LINENUMBER 55 L8
        //    NEW java/lang/Error
        //    DUP
        //    LDC "Could not create new dimension"
        //    ALOAD 1
        //    INVOKESPECIAL java/lang/Error.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
        //    ATHROW
        //   L4
        //    LINENUMBER 57 L4
        //   FRAME SAME1 java/lang/InstantiationException
        //    ASTORE 1
        //   L9
        //    LINENUMBER 59 L9
        //    NEW java/lang/Error
        //    DUP
        //    LDC "Could not create new dimension"
        //    ALOAD 1
        //    INVOKESPECIAL java/lang/Error.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
        //    ATHROW
        //   L5
        //    LINENUMBER 61 L5
        //   FRAME SAME1 java/lang/IllegalAccessException
        //    ASTORE 1
        //   L10
        //    LINENUMBER 63 L10
        //    NEW java/lang/Error
        //    DUP
        //    LDC "Could not create new dimension"
        //    ALOAD 1
        //    INVOKESPECIAL java/lang/Error.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
        //    ATHROW
        //   L11
        //    LOCALVARIABLE constructor Ljava/lang/reflect/Constructor; L6 L2 1
        //    // signature Ljava/lang/reflect/Constructor<+Lnet/minecraft/world/WorldProvider;>;
        //    // declaration: constructor extends java.lang.reflect.Constructor<? extends net.minecraft.world.WorldProvider>
        //    LOCALVARIABLE nosuchmethodexception Ljava/lang/NoSuchMethodException; L7 L3 1
        //    LOCALVARIABLE invocationtargetexception Ljava/lang/reflect/InvocationTargetException; L8 L4 1
        //    LOCALVARIABLE instantiationexception Ljava/lang/InstantiationException; L9 L5 1
        //    LOCALVARIABLE illegalaccessexception Ljava/lang/IllegalAccessException; L10 L11 1
        //    LOCALVARIABLE this Lnet/minecraft/world/DimensionType; L0 L11 0
        //    MAXSTACK = 4
        //    MAXLOCALS = 2

        private final Logger logger;

        private boolean injected;

        CreateDimensionMethodVisitor(final int version, final MethodVisitor parent, final Logger logger) {
            super(version, parent);
            this.logger = logger;
            this.injected = false;
        }

        @Override
        public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
            if (!this.injected && Opcodes.GETFIELD == opcode && DIMENSION_TYPE.equals(owner) && MappingUtilities.INSTANCE.mapField("field_186077_g").equals(name)) {
                this.logger.info("Identified attempt to read clazz: redirecting to supplier");

                super.visitFieldInsn(Opcodes.GETFIELD, DIMENSION_TYPE, CLASS_SUPPLIER_FIELD_NAME, String.format("L%s;", SUPPLIER));
                super.visitMethodInsn(Opcodes.INVOKEINTERFACE, SUPPLIER, "get", "()Ljava/lang/Object;", true);
                super.visitTypeInsn(Opcodes.CHECKCAST, CLASS);

                this.injected = true;
                return;
            }

            super.visitFieldInsn(opcode, owner, name, desc);
        }
    }

    private static final class FinderMethodInjector extends MethodVisitor {
        //  // access flags 0x101A
        //  private static final synthetic fermion-injected-method$dimension-hopper-tweaks$find(Ljava/lang/String;)Lnet/minecraft/world/DimensionType;
        //   L0
        //    LINENUMBER 400 L0
        //    ALOAD 0
        //    INVOKESTATIC net/minecraft/world/DimensionType.func_193417_a (Ljava/lang/String;)Lnet/minecraft/world/DimensionType;
        //    ARETURN
        //   L1
        //    LOCALVARIABLE $$0 Ljava/lang/String; L0 L1 0
        //    MAXSTACK = 1
        //    MAXLOCALS = 1

        private final Logger logger;

        private FinderMethodInjector(final int version, final MethodVisitor parent, final Logger logger) {
            super(version, parent);
            this.logger = logger;
        }

        public static void inject(final int version, final ClassVisitor visitor, final Logger logger) {
            final MethodVisitor parent = visitor.visitMethod(
                    Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL | Opcodes.ACC_SYNTHETIC,
                    FINDER_NAME,
                    FINDER_DESC,
                    null,
                    null
            );
            final MethodVisitor creator = new FinderMethodInjector(version, parent, logger);
            creator.visitCode();
        }

        @Override
        public void visitCode() {
            this.logger.info("Creating code for finder method");

            super.visitCode();

            final Label l0 = new Label();
            super.visitLabel(l0);
            super.visitLineNumber(400, l0);
            super.visitVarInsn(Opcodes.ALOAD, 0);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, DIMENSION_TYPE, MappingUtilities.INSTANCE.mapMethod("func_193417_a"), FINDER_DESC, false);
            super.visitInsn(Opcodes.ARETURN);

            final Label l1 = new Label();
            super.visitLabel(l1);

            super.visitLocalVariable("$$0", "Ljava/lang/String;", null, l0, l1, 0);
            super.visitMaxs(1, 1);

            super.visitEnd();
        }
    }

    private static final class ConstructorInjector extends MethodVisitor {
        //  // access flags 0x1002
        //  // signature (ILjava/lang/String;Ljava/lang/String;Ljava/util/function/Supplier<Ljava/lang/Class<+Lnet/minecraft/world/WorldProvider;>;>;Z)V
        //  // declaration: void <init>(int, java.lang.String, java.lang.String, java.util.function.Supplier<java.lang.Class<? extends net.minecraft.world.WorldProvider>>, boolean)
        //  private synthetic <init>(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/util/function/Supplier;Z)V
        //   L0
        //    LINENUMBER 500 L0
        //    ALOAD 0
        //    ALOAD 1
        //    ILOAD 2
        //    INVOKESPECIAL java/lang/Enum.<init> (Ljava/lang/String;I)V
        //   L1
        //    LINENUMBER 501 L1
        //    ALOAD 0
        //    ILOAD 3
        //    PUTFIELD net/minecraft/world/DimensionType.field_186074_d : I
        //   L2
        //    LINENUMBER 502 L2
        //    ALOAD 0
        //    ALOAD 4
        //    PUTFIELD net/minecraft/world/DimensionType.field_186075_e : Ljava/lang/String;
        //   L3
        //    LINENUMBER 503 L3
        //    ALOAD 0
        //    ALOAD 5
        //    PUTFIELD net/minecraft/world/DimensionType.field_186076_f : Ljava/lang/String;
        //   L4
        //    LINENUMBER 504 L4
        //    ALOAD 0
        //    ALOAD 6
        //    PUTFIELD net/minecraft/world/DimensionType.fermion-injected-field$dimension-hopper-tweaks$classSupplier : Ljava/util/function/Supplier;
        //   L5
        //    LINENUMBER 505 L5
        //    ALOAD 0
        //    ILOAD 7
        //    PUTFIELD net/minecraft/world/DimensionType.shouldLoadSpawn : Z
        //   L6
        //    LINENUMBER 506 L6
        //    ACONST_NULL
        //    ASTORE 8
        //   L7
        //    LINENUMBER 507 L7
        //    ALOAD 0
        //    ALOAD 8
        //    PUTFIELD net/minecraft/world/DimensionType.field_186077_g : Ljava/lang/Class;
        //   L8
        //    LINENUMBER 508 L8
        //    RETURN
        //   L9
        //    LOCALVARIABLE this Lnet/minecraft/world/DimensionType; L0 L9 0
        //    LOCALVARIABLE $$1 Ljava/lang/String; L0 L9 1
        //    LOCALVARIABLE $$2 I L0 L9 2
        //    LOCALVARIABLE $$3 I L0 L9 3
        //    LOCALVARIABLE $$4 Ljava/lang/String; L0 L9 4
        //    LOCALVARIABLE $$5 Ljava/lang/String; L0 L9 5
        //    LOCALVARIABLE $$6 Ljava/util/function/Supplier; L0 L9 6
        //    // signature Ljava/util/function/Supplier<Ljava/lang/Class<+Lnet/minecraft/world/WorldProvider;>;>;
        //    // declaration: $$6 extends java.util.function.Supplier<java.lang.Class<? extends net.minecraft.world.WorldProvider>>
        //    LOCALVARIABLE $$7 Z L0 L9 7
        //    LOCALVARIABLE $$8 Ljava/lang/Class; L7 L9 8
        //    // signature Ljava/lang/Class<*>;
        //    // declaration: $$8 extends java.lang.Class<?>
        //    MAXSTACK = 3
        //    MAXLOCALS = 9

        private final Logger logger;

        private ConstructorInjector(final int version, final MethodVisitor parent, final Logger logger) {
            super(version, parent);
            this.logger = logger;
        }

        public static void inject(final int version, final ClassVisitor visitor, final Logger logger) {
            final MethodVisitor parent = visitor.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_SYNTHETIC, DT_NEW_INIT_NAME, DT_NEW_INIT_DESC, null, null);
            final MethodVisitor creator = new ConstructorInjector(version, parent, logger);
            creator.visitCode();
        }

        @Override
        public void visitCode() {
            this.logger.info("Generating new synthetic constructor for supplier-based access");

            super.visitCode();

            final Label l0 = new Label();
            super.visitLabel(l0);
            super.visitLineNumber(500, l0);
            super.visitVarInsn(Opcodes.ALOAD, 0);
            super.visitVarInsn(Opcodes.ALOAD, 1);
            super.visitVarInsn(Opcodes.ILOAD, 2);
            super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Enum", "<init>", "(Ljava/lang/String;I)V", false);

            this.field(INTEGER, 3, MappingUtilities.INSTANCE.mapField("field_186074_d"), "I");
            this.field(OBJECT, 4, MappingUtilities.INSTANCE.mapField("field_186075_e"), "Ljava/lang/String;");
            this.field(OBJECT, 5, MappingUtilities.INSTANCE.mapField("field_186076_f"), "Ljava/lang/String;");
            this.field(OBJECT, 6, CLASS_SUPPLIER_FIELD_NAME, String.format("L%s;", SUPPLIER));
            this.field(INTEGER, 7, "shouldLoadSpawn", "Z");

            final Label l6 = new Label();
            super.visitLabel(l6);
            super.visitLineNumber(506, l6);
            super.visitInsn(Opcodes.ACONST_NULL);
            super.visitVarInsn(Opcodes.ASTORE, 8);

            final Label l7 = this.field(OBJECT, 8, MappingUtilities.INSTANCE.mapField("field_186077_g"), String.format("L%s;", CLASS));

            final Label l8 = new Label();
            super.visitLabel(l8);
            super.visitLineNumber(508, l8);
            super.visitInsn(Opcodes.RETURN);

            final Label l9 = new Label();
            super.visitLabel(l9);

            super.visitLocalVariable("this", String.format("L%s;", DIMENSION_TYPE), null, l0, l9, 0);
            super.visitLocalVariable("$$1", "Ljava/lang/String;", null, l0, l9, 1);
            super.visitLocalVariable("$$2", "I", null, l0, l9, 2);
            super.visitLocalVariable("$$3", "I", null, l0, l9, 3);
            super.visitLocalVariable("$$4", "Ljava/lang/String;", null, l0, l9, 4);
            super.visitLocalVariable("$$5", "Ljava/lang/String;", null, l0, l9, 5);
            super.visitLocalVariable("$$6", String.format("L%s;", SUPPLIER), String.format("L%s<L%s<+Lnet/minecraft/world/WorldProvider;>;>;", SUPPLIER, CLASS), l0, l9, 7);
            super.visitLocalVariable("$$7", "Z", null, l0, l9, 7);
            super.visitLocalVariable("$$8", String.format("L%s;", CLASS), String.format("L%s<*>;", CLASS), l7, l9, 8);
            super.visitMaxs(3, 9);

            super.visitEnd();
        }

        private Label field(final int type, final int index, final String name, final String desc) {
            final Label label = new Label();
            super.visitLabel(label);
            super.visitLineNumber(498 + index + (index == 8? 1 : 0), label);
            super.visitVarInsn(Opcodes.ALOAD, 0);
            this.load(type, index);
            super.visitFieldInsn(Opcodes.PUTFIELD, DIMENSION_TYPE, name, desc);
            return label;
        }

        private void load(final int type, final int index) {
            switch (type) {
                case OBJECT:
                    super.visitVarInsn(Opcodes.ALOAD, index);
                    break;
                case INTEGER:
                    super.visitVarInsn(Opcodes.ILOAD, index);
                    break;
                default:
                    throw new IllegalArgumentException(Integer.toString(type, 16));
            }
        }
    }

    private static final class FieldInjector {
        // [for every dimension {data = dimension data}]
        //  // access flags 0x4019
        //  public final static enum Lnet/minecraft/world/DimensionType; {data.enumerationName}
        // [end for]
        //
        //  // access flags 0x12
        //  // signature Ljava/util/function/Supplier<Ljava/lang/Class<+Lnet/minecraft/world/WorldProvider;>;>;
        //  // declaration: fermion-injected-field$dimension-hopper-tweaks$classSupplier extends java.util.function.Supplier<java.lang.Class<? extends net.minecraft.world.WorldProvider>>
        //  private final Ljava/util/function/Supplier; fermion-injected-field$dimension-hopper-tweaks$classSupplier

        public static void inject(final ClassVisitor visitor, final Logger logger) {
            logger.info("Injecting all dimension fields");
            DIMENSIONS_TO_ADD.forEach(data -> visitor.visitField(
                    Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL | Opcodes.ACC_ENUM,
                    data.enumerationName(),
                    String.format("L%s;", DIMENSION_TYPE),
                    null,
                    null
            ));

            logger.info("Injecting supplier field");
            visitor.visitField(
                    Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL,
                    CLASS_SUPPLIER_FIELD_NAME,
                    String.format("L%s;", SUPPLIER),
                    String.format("L%s<L%s<+Lnet/minecraft/world/WorldProvider;>;>;", SUPPLIER, CLASS),
                    null
            );
        }
    }

    private static final class IdentityLambdaInjector extends MethodVisitor {
        //  // access flags 0x101A
        //  // signature (Ljava/lang/Class<*>;)Ljava/lang/Class<*>;
        //  // declaration: private static final java.lang.Class<?> fermion-injected-lambda$dimension-hopper-tweaks$supplier$identity-for-class(java.lang.Class<?>)
        //  private static final synthetic fermion-injected-lambda$dimension-hopper-tweaks$supplier$identity-for-class (Ljava/lang/Class;)Ljava/lang/Class;
        //   L0
        //    LINENUMBER 1990 L0
        //    ALOAD 0
        //    ARETURN
        //   L1
        //    LOCALVARIABLE $$0 Ljava/lang/Class; L0 L1
        //    // signature Ljava/lang/Class<*>;
        //    // declaration: $$0 extends java.lang.Class<?>
        //    MAXSTACK = 1
        //    MAXLOCALS = 1

        private final Logger logger;

        private IdentityLambdaInjector(final int version, final MethodVisitor parent, final Logger logger) {
            super(version, parent);
            this.logger = logger;
        }

        public static void inject(final int version, final ClassVisitor visitor, final Logger logger) {
            final MethodVisitor parent = visitor.visitMethod(
                    Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL | Opcodes.ACC_SYNTHETIC,
                    CLASS_IDENTITY_NAME,
                    CLASS_IDENTITY_DESC,
                    String.format("(L%s<*>;)L%s<*>;", CLASS, CLASS),
                    null
            );
            final MethodVisitor creator = new IdentityLambdaInjector(version, parent, logger);
            creator.visitCode();
        }

        @Override
        public void visitCode() {
            logger.info("Injecting identity function for classes, leveraged by the supplier");

            super.visitCode();

            final Label l0 = new Label();
            super.visitLabel(l0);
            super.visitLineNumber(1990, l0);
            super.visitVarInsn(Opcodes.ALOAD, 0);
            super.visitInsn(Opcodes.ARETURN);

            final Label l1 = new Label();
            super.visitLabel(l1);

            super.visitLocalVariable("$$0", String.format("L%s;", CLASS), String.format("L%s<*>;", CLASS), l0, l1, 0);
            super.visitMaxs(1, 1);

            super.visitEnd();
        }
    }

    private static final class SupplierLambdaInjector extends MethodVisitor {
        // [for every dimension {i = index, data = dimension data}]
        //  // access flags 0x101A
        //  // signature ()Ljava/lang/Class<+Lnet/minecraft/world/WorldProvider;>;
        //  // declaration: private static final java.lang.Class<? extends net.minecraft.world.WorldProvider> fermion-injected-lambda$dimension-hopper-tweaks$supplier$targeting-{i}()
        //  private static final synthetic fermion-injected-lambda$dimension-hopper-tweaks$supplier$targeting-{i} ()Ljava/lang/Class;
        //   L0
        //    LINENUMBER {1200 + i} L0
        //    LDC {data.worldProviderClassName}
        //    INVOKESTATIC java/lang/Class.forName (Ljava/lang/String;)Ljava/lang/Class;
        //    ARETURN
        //   L1
        //    MAXSTACK = 1
        //    MAXLOCALS = 0
        // [end for]

        private final int index;

        private SupplierLambdaInjector(final int version, final MethodVisitor parent, final int index) {
            super(version, parent);
            this.index = index;
        }

        public static void inject(final int version, final ClassVisitor visitor, final Logger logger) {
            logger.info("Performing massively and extremely invasive lambda injection");

            for (int i = 0, s = DIMENSIONS_TO_ADD.size(); i < s; ++i) {
                final MethodVisitor parent = visitor.visitMethod(
                        Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL | Opcodes.ACC_SYNTHETIC,
                        targetingSupplierName(i),
                        String.format("()L%s;", CLASS),
                        String.format("()L%s<+Lnet/minecraft/world/WorldProvider;>;", CLASS),
                        null
                );
                final MethodVisitor creator = new SupplierLambdaInjector(version, parent, i);
                creator.visitCode();
            }
        }

        @Override
        public void visitCode() {
            super.visitCode();

            final Label l0 = new Label();
            super.visitLabel(l0);
            super.visitLineNumber(1200 + this.index, l0);
            super.visitLdcInsn(DIMENSIONS_TO_ADD.get(this.index).worldProviderClassName());
            super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS, "forName", String.format("(Ljava/lang/String;)L%s;", CLASS), false);
            super.visitInsn(Opcodes.ARETURN);

            final Label l1 = new Label();
            super.visitLabel(l1);

            super.visitMaxs(1, 0);

            super.visitEnd();
        }
    }

    // This should be a record, but alas Java 8
    private static final class DimensionInjectionData {
        private final String enumerationName;
        private final int id;
        private final String dimensionName;
        private final String suffix;
        private final String worldProviderClassName;
        private final boolean shouldKeepLoaded;

        DimensionInjectionData(
                final String enumerationName,
                final int id,
                final String dimensionName,
                final String suffix,
                final String worldProviderClassName,
                final boolean shouldKeepLoaded
        ) {
            this.enumerationName = Objects.requireNonNull(enumerationName);
            this.id = id;
            this.dimensionName = Objects.requireNonNull(dimensionName);
            this.suffix = Objects.requireNonNull(suffix);
            this.worldProviderClassName = Objects.requireNonNull(worldProviderClassName);
            this.shouldKeepLoaded = shouldKeepLoaded;
        }

        String enumerationName() {
            return this.enumerationName;
        }

        int id() {
            return this.id;
        }

        String dimensionName() {
            return this.dimensionName;
        }

        String suffix() {
            return this.suffix;
        }

        String worldProviderClassName() {
            return this.worldProviderClassName;
        }

        boolean shouldKeepLoaded() {
            return this.shouldKeepLoaded;
        }
    }

    private static final int OBJECT = 0x1368;
    private static final int INTEGER = 0x1369;

    private static final String CLASS = ClassDescriptor.of(Class.class).toAsmName();
    private static final String DIMENSION_TYPE = "net/minecraft/world/DimensionType";
    private static final String LAMBDA_METAFACTORY = ClassDescriptor.of(LambdaMetafactory.class).toAsmName();
    private static final String LAZY = "mods/thecomputerizer/dimensionhoppertweaks/util/Lazy";
    private static final String SUPPLIER = ClassDescriptor.of(Supplier.class).toAsmName();

    private static final String CLASS_IDENTITY_NAME = "fermion-injected-lambda$dimension-hopper-tweaks$supplier$identity-for-class";
    private static final String CLASS_IDENTITY_DESC = MethodDescriptor.of(
            CLASS_IDENTITY_NAME,
            ImmutableList.of(ClassDescriptor.of(Class.class)),
            ClassDescriptor.of(Class.class)
    ).toAsmDescriptor();

    private static final String CLASS_SUPPLIER_FIELD_NAME = "fermion-injected-field$dimension-hopper-tweaks$classSupplier";

    private static final String DT_NEW_INIT_NAME = "<init>";
    private static final String DT_NEW_INIT_DESC = MethodDescriptor.of(
            DT_NEW_INIT_NAME,
            ImmutableList.of(
                    ClassDescriptor.of(String.class),
                    ClassDescriptor.of(int.class),
                    ClassDescriptor.of(int.class),
                    ClassDescriptor.of(String.class),
                    ClassDescriptor.of(String.class),
                    ClassDescriptor.of(Supplier.class),
                    ClassDescriptor.of(boolean.class)
            ),
            ClassDescriptor.of(void.class)
    ).toAsmDescriptor();

    private static final String FINDER_NAME = "fermion-injected-method$dimension-hopper-tweaks$find";
    private static final String FINDER_DESC = MethodDescriptor.of(
            FINDER_NAME,
            ImmutableList.of(ClassDescriptor.of(String.class)),
            ClassDescriptor.of(DIMENSION_TYPE)
    ).toAsmDescriptor();

    private static final String LAZY_OF_NAME = "of";
    private static final String LAZY_OF_DESC = MethodDescriptor.of(
            LAZY_OF_NAME,
            ImmutableList.of(ClassDescriptor.of(SUPPLIER)),
            ClassDescriptor.of(LAZY)
    ).toAsmDescriptor();

    private static final String METAFACTORY_NAME = "metafactory";
    private static final String METAFACTORY_DESC = MethodDescriptor.of(
            METAFACTORY_NAME,
            ImmutableList.of(
                    ClassDescriptor.of(MethodHandles.Lookup.class),
                    ClassDescriptor.of(String.class),
                    ClassDescriptor.of(MethodType.class),
                    ClassDescriptor.of(MethodType.class),
                    ClassDescriptor.of(MethodHandle.class),
                    ClassDescriptor.of(MethodType.class)
            ),
            ClassDescriptor.of(CallSite.class)
    ).toAsmDescriptor();

    private static final List<DimensionInjectionData> DIMENSIONS_TO_ADD = ImmutableList.of(
            // TODO("Add dimensions to the list")
    );

    private final Logger logger;

    DimensionTypeTransformer(final LaunchPlugin owner, final Logger logger) {
        super(
                TransformerData.Builder.create()
                        .setOwningPlugin(owner)
                        .setName("dimension_type")
                        .setDescription("Transforms DimensionType to automatically account for all necessary dimensions")
                        .setDisabledByDefault()
                        .build(),
                ClassDescriptor.of(DIMENSION_TYPE)
        );
        this.logger = logger;
        forceInnerInit();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void forceInnerInit() { // Force initialization to work around dumb 1.12.2 classloading
        StaticInitializationMethodVisitor.class.toString();
        RegisterMethodVisitor.class.toString();
        ConstructorVisitor.class.toString();
        CreateDimensionMethodVisitor.class.toString();
        FinderMethodInjector.class.toString();
        ConstructorInjector.class.toString();
        FieldInjector.class.toString();
        IdentityLambdaInjector.class.toString();
        SupplierLambdaInjector.class.toString();
    }

    private static String targetingSupplierName(final int index) {
        return "fermion-injected-lambda$dimension-hopper-tweaks$supplier$targeting" + index;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, ClassVisitor, ClassVisitor> getClassVisitorCreator() {
        return (v, parent) -> new ClassVisitor(v, parent) {
            @Override
            public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
                super.visit(version, access, name, signature, superName, interfaces);
                logger.info("Initiated DimensionType extremely invasive transformation: here be dragons");
            }

            @Override
            public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
                final MethodVisitor parent = super.visitMethod(access, name, desc, signature, exceptions);
                if ("<clinit>".equals(name)) {
                    logger.info("Static initializer {} identified: attempting transformation", name);
                    return new StaticInitializationMethodVisitor(v, parent, logger);
                }
                if ("<init>".equals(name) && !DT_NEW_INIT_DESC.equals(desc)) {
                    logger.info("Constructor identified ({} {}): attempting transformation", name, desc);
                    return new ConstructorVisitor(v, parent, logger);
                }
                if ("register".equals(name)) {
                    logger.info("Register method identified ({} {}): attempting transformation", name, desc);
                    return new RegisterMethodVisitor(v, parent, logger);
                }
                if (MappingUtilities.INSTANCE.mapMethod("func_186070_d").equals(name)) {
                    logger.info("Found create dimension or equivalent SRG in {} {}: attempting transformation", name, desc);
                    return new CreateDimensionMethodVisitor(v, parent, logger);
                }
                return parent;
            }

            @Override
            public void visitEnd() {
                logger.info("Reached end of class: injecting additional methods and fields");
                FinderMethodInjector.inject(v, this, logger);
                ConstructorInjector.inject(v, this, logger);
                FieldInjector.inject(this, logger);
                IdentityLambdaInjector.inject(v, this, logger);
                SupplierLambdaInjector.inject(v, this, logger);
                super.visitEnd();
                logger.info("Transformation complete: here be dragons... AGAIN");
            }
        };
    }
}
