(function() {
    function getQueryParam(name, queryString) {
        var match = RegExp(name + '=([^&]*)').exec(queryString || location.search);
        return match && decodeURIComponent(match[1]);
    }

    function hasOption(opt) {
        var s = window.location.search;
        var re = new RegExp('(?:^|[&?])' + opt + '(?:[=]([^&]*))?(?:$|[&])', 'i');
        var m = re.exec(s);

        return m ? (m[1] === undefined ? true : m[1]) : false;
    }

    var scriptTags = document.getElementsByTagName('script'),
        defaultTheme = 'triton',
        defaultRtl = false,
        i = scriptTags.length,
        requires = [
            'Ext.window.MessageBox',
            'Ext.toolbar.Toolbar',
            'Ext.form.field.ComboBox',
            'Ext.form.FieldContainer',
            'Ext.form.field.Radio'

        ],
        comboWidth = {
            classic: 160,
            gray: 160,
            neptune: 180,
            triton: 180,
            crisp: 180,
            'neptune-touch': 220,
            'crisp-touch': 220
        },
        labelWidth = {
            classic: 40,
            gray: 40,
            neptune: 45,
            triton: 45,
            crisp: 45,
            'neptune-touch': 55,
            'crisp-touch': 55
        },
        defaultQueryString, src, theme, rtl, toolbar;

    while (i--) {
        src = scriptTags[i].src;
        if (src.indexOf('include-ext.js') !== -1) {
            defaultQueryString = src.split('?')[1];
            if (defaultQueryString) {
                defaultTheme = getQueryParam('theme', defaultQueryString) || defaultTheme;
                defaultRtl = getQueryParam('rtl', defaultQueryString) || defaultRtl;
            }
            break;
        }
    }

    Ext.themeName = theme = getQueryParam('theme') || defaultTheme;
    
    rtl = getQueryParam('rtl') || defaultRtl;

    if (rtl.toString() === 'true') {
        requires.unshift('Ext.rtl.*');
        Ext.define('Ext.examples.RtlComponent', {
            override: 'Ext.Component',
            rtl: true
        });
    }

    Ext.require(requires);

    Ext.onReady(function() {
        Ext.getBody().addCls(Ext.baseCSSPrefix + 'theme-' + Ext.themeName);

        // prevent touchmove from panning the viewport in mobile safari
        if (Ext.supports.TouchEvents) {
            Ext.getDoc().on({
                touchmove: function(e) {
                    // If within a scroller, don't let the document use it
                    if (Ext.scroll.Scroller.isTouching) {
                        e.preventDefault();
                    }
                },
                translate: false,
                delegated: false
            });
        }

        if (hasOption('nocss3')) {
            Ext.supports.CSS3BorderRadius = false;
            Ext.getBody().addCls('x-nbr x-nlg');
        }

        if (hasOption('nlg')) {
            Ext.getBody().addCls('x-nlg');
        }

        function setParam(param) {
            var queryString = Ext.Object.toQueryString(
                Ext.apply(Ext.Object.fromQueryString(location.search), param)
            );
            location.search = queryString;
        }

        function removeParam(paramName) {
            var params = Ext.Object.fromQueryString(location.search);

            delete params[paramName];

            location.search = Ext.Object.toQueryString(params);
        }
        
        if (hasOption('no-toolbar') || /no-toolbar/.test(document.cookie)) {
            return;
        }

        

    });
})();
