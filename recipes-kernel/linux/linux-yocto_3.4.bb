require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-custom:"

LINUX_VERSION = "3.4.0"
KBRANCH = "vtss_3.4"
KMETA = ""

SRC_URI = "git://soft02/proj/sw/git/linux.git;branch=${KBRANCH};protocol=ssh;bareclone=1"
SRC_URI += "file://defconfig"

SRCREV="${AUTOREV}"

PR = "r1"
PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_luton26 = "luton26"
#KBRANCH_luton26 = "vtss_3.5"
SRC_URI_append_luton26 = " file://luton26.scc file://luton26.cfg"

COMPATIBLE_MACHINE_jaguar1 = "jaguar1"
#KBRANCH_jaguar1 = "vtss_3.5"
SRC_URI_append_jaguar1 = " file://jaguar1.scc file://jaguar1.cfg"

COMPATIBLE_MACHINE_serval1 = "serval1"
#KBRANCH_serval1 = "vtss_3.5"
SRC_URI_append_serval1 = " file://serval1.scc file://serval1.cfg"

kernel_do_deploy_append() {
    pwd
    ls -l
    ${OBJCOPY} -O binary -R .note -R .comment -S vmlinux vmlinux.bin
    gzip -9c < vmlinux.bin > ${DEPLOYDIR}/vmlinux.gz
    rm -f linux.bin
}
