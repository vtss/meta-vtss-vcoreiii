# This file was derived from the linux-yocto-custom.bb recipe in
# oe-core.
#
# linux-yocto-custom.bb:
#
#   A yocto-bsp-generated kernel recipe that uses the linux-yocto and
#   oe-core kernel classes to apply a subset of yocto kernel
#   management to git managed kernel repositories.
#
# Warning:
#
#   Building this kernel without providing a defconfig or BSP
#   configuration will result in build or boot errors. This is not a
#   bug.
#
# Notes:
#
#   patches: patches can be merged into to the source git tree itself,
#            added via the SRC_URI, or controlled via a BSP
#            configuration.
#
#   example configuration addition:
#            SRC_URI += "file://smp.cfg"
#   example patch addition:
#            SRC_URI += "file://0001-linux-version-tweak.patch
#   example feature addition:
#            SRC_URI += "file://feature.scc"
#

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://soft02/proj/sw/git/linux.git;branch=vtss_3.5;protocol=ssh;bareclone=1"
SRC_URI += "file://defconfig"

LINUX_VERSION ?= "3.5"
LINUX_VERSION_EXTENSION ?= "-custom"

SRCREV="${AUTOREV}"

PR = "r1"
PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_luton26 = "luton26"
KBRANCH_luton26 = "vtss_3.5"
SRC_URI_append_luton26 = " file://luton26.scc file://luton26.cfg"

COMPATIBLE_MACHINE_jaguar1 = "jaguar1"
KBRANCH_jaguar1 = "vtss_3.5"
SRC_URI_append_jaguar1 = " file://jaguar1.scc file://jaguar1.cfg"

COMPATIBLE_MACHINE_serval1 = "serval1"
KBRANCH_serval1 = "vtss_3.5"
SRC_URI_append_serval1 = " file://serval1.scc file://serval1.cfg"

kernel_do_deploy_append() {
    pwd
    ls -l
    ${OBJCOPY} -O binary -R .note -R .comment -S vmlinux vmlinux.bin
    gzip -9c < vmlinux.bin > ${DEPLOYDIR}/vmlinux.gz
    rm -f linux.bin
}
