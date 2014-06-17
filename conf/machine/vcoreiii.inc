#@TYPE: Machine
#@NAME: vcoreiii

#@DESCRIPTION: Machine configuration for vcoreiii systems

require conf/machine/include/tune-mips32.inc

DEFAULTTUNE = "mips32el-nf"

MACHINE_FEATURES = "ext2 ext3 serial"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGE_STRIP_EXTRA_SECTIONS  = ".comment"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-yocto"
PREFERRED_VERSION_linux-yocto ?= "3.14%"

SERIAL_CONSOLE = "115200 ttyS0"

MACHINE_EXTRA_RRECOMMENDS = " kernel-modules"

IMAGE_FSTYPES ?= "squashfs-xz jffs2"

IMAGE_DEPENDS_squashfs-xz = "squashfs-tools-native"
