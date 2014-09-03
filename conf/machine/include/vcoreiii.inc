#@TYPE: Machine
#@NAME: vcoreiii

#@DESCRIPTION: Machine configuration for vcoreiii systems

include conf/machine/include/soc-family.inc
require conf/machine/include/tune-mips32.inc

DEFAULTTUNE = "mips32el-nf"

MACHINE_FEATURES = "ext2 ext3 serial"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGE_STRIP_EXTRA_SECTIONS  = ".comment"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-yocto"
PREFERRED_VERSION_linux-yocto ?= "3.14%"

SERIAL_CONSOLE = "115200 ttyS0"

MACHINE_EXTRA_RRECOMMENDS = " kernel-modules"

# No getty by default
SYSVINIT_ENABLED_GETTYS = ""

# Loose the RTC - don't have it
MACHINE_FEATURES_BACKFILL_CONSIDERED = "rtc"

IMAGE_FSTYPES ?= "squashfs-xz"

IMAGE_DEPENDS_squashfs-xz = "squashfs-tools-native"
