require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

LINUX_VERSION = "3.14"
KBRANCH = "vtss_${LINUX_VERSION}"
KMETA = ""

SRC_URI = "git://github.com/vtss/linux-stable.git;protocol=git;bareclone=1;branch=${KBRANCH}"
SRC_URI += "file://defconfig"

SRCREV="${AUTOREV}"

PR = "r1"
PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_luton26 = "luton26"
SRC_URI_append_luton26 = " file://luton26.scc file://luton26.cfg"

COMPATIBLE_MACHINE_luton10 = "luton10"
SRC_URI_append_luton10 = " file://luton26.scc file://luton26.cfg"
KMACHINE_luton10 = "luton26"

COMPATIBLE_MACHINE_jaguar1 = "jaguar1"
SRC_URI_append_jaguar1 = " file://jaguar1.scc file://jaguar1.cfg"

COMPATIBLE_MACHINE_serval1 = "serval1"
SRC_URI_append_serval1 = " file://serval1.scc file://serval1.cfg"

COMPATIBLE_MACHINE_jaguar2 = "jaguar2"
SRC_URI_append_jaguar2 = " file://jaguar2.scc file://jaguar2.cfg"

COMPATIBLE_MACHINE_serval2 = "serval2"
SRC_URI_append_serval2 = " file://jaguar2.scc file://jaguar2.cfg"
KMACHINE_serval2 = "jaguar2"

kernel_do_deploy_append() {
    pwd
    ls -l
    ${OBJCOPY} -O binary -R .note -R .comment -S vmlinux vmlinux.bin
    gzip -9c < vmlinux.bin > ${DEPLOYDIR}/vmlinux-${LINUX_VERSION}.${MACHINE}.gz
    rm -f linux.bin
}
