FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://etc/preinit \
	     file://etc/diag.sh \
	     file://etc/functions.sh \
	     file://sbin/mount_root \
	     file://bin/firstboot \
"
S = "${WORKDIR}"

do_install_append() {
    #
    # Create directories:
    #   ${D}${sysconfdir}/init.d - will hold the scripts
    #   ${D}${sysconfdir}/rcS.d  - will contain a link to the script that runs at startup
    #   ${D}${sysconfdir}/rc5.d  - will contain a link to the script that runs at runlevel=5
    #   ${D}${sbindir}           - scripts called by the above
    #
    # ${D} is effectively the root directory of the target system.
    # ${D}${sysconfdir} is where system configuration files are to be stored (e.g. /etc).
    # ${D}${sbindir} is where executable files are to be stored (e.g. /sbin).
    #
    install -d ${D}/jffs
    install -d ${D}/mmc
    install -d ${D}/rom
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rcS.d
    install -d ${D}${sysconfdir}/rc1.d
    install -d ${D}${sysconfdir}/rc2.d
    install -d ${D}${sysconfdir}/rc3.d
    install -d ${D}${sysconfdir}/rc4.d
    install -d ${D}${sysconfdir}/rc5.d
    install -d ${D}${sbindir}

    #
    # Install files in to the image
    #
    # The files fetched via SRC_URI (above) will be in ${WORKDIR}.
    install -m 0775 ${WORKDIR}/etc/preinit	${D}${sysconfdir}
    install -m 0775 ${WORKDIR}/etc/diag.sh 	${D}${sysconfdir}
    install -m 0775 ${WORKDIR}/etc/functions.sh ${D}${sysconfdir}
    install -m 0775 ${WORKDIR}/sbin/mount_root 	${D}${base_sbindir}
    install -m 0775 ${WORKDIR}/bin/firstboot 	${D}${base_bindir}


    #
    # Symbolic links can also be installed. e.g.
    #
    # ln -s support-script-link ${D}${sbindir}/support-script

    #
    # Create symbolic links from the runlevel directories to the script files.
    # Links of the form S... and K... mean the script when be called when
    # entering / exiting the runlevel designated by the containing directory.
    # For example:
    #   rc5.d/S90run-script will be called (with %1='start') when entering runlevel 5.
    #   rc5.d/K90run-script will be called (with %1='stop') when exiting runlevel 5.
    #
}

