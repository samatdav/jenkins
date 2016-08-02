Behaviour.specify("A.help-button", 'hudson-behavior', 1, function(e) {
    e.onclick = function() {
        var tr = findFollowingTR(this, "help-area");            
        var thistr = $(this).up().up();

        if (!$(tr).hasClassName("custom-help")) {
            var customtr = document.createElement("tr");
            customtr.innerHTML = '  <td></td><td colspan="2"><div class="help"><div class="custom-hep-button">'+
            '<div class="custom-hep-button-save" onclick="save_custom_help(this)" >Save</div><div class="custom-hep-button-edit" onclick="edit_custom_help(this)" >Edit</div>'+
            '</div><div class="custom-hep-text">Loading1...</div></div></td><td></td>';
            $(customtr).addClassName("help-area");
            $(customtr).addClassName("custom-help");
            $(thistr).insert({after:customtr});
            $(customtr).down().next().down().down().down().next().style.display = "block";
            var div = $(tr).down().next().down();
            var div2 = $(customtr).down().next().down().down().next()
            var div3 = $(customtr).down().next().down();
            div.style.display = "block";
            div3.style.display = "block";
            // make it visible
            new Ajax.Request(this.getAttribute("helpURL"), {
                method : 'get',
                onSuccess : function(x) {
                    var from = x.getResponseHeader("X-Plugin-From");
                    div.innerHTML = x.responseText+(from?"<div class='from-plugin'>"+from+"</div>":"");
                    layoutUpdateCallback.call();
                },
                onFailure : function(x) {
                    div.innerHTML = "<b>ERROR</b>: Failed to load help file: " + x.statusText;
                    layoutUpdateCallback.call();
                }
            });
            
            if(this.getAttribute("helpURL").indexOf('project-config') + 1) {

                var mass1= this.getAttribute("helpURL").split('/');
                var i = 0;
                for (i = 0; i < mass1.length; i++) {
                    if(mass1[i] == 'help')
                        break;
                }
                var customurl = "";

                for (var j = 0; j < i; j++) {
                    customurl += mass1[j]+"/";
                }
                var customurl2 = customurl;
                var customurl3 = "projectconfig"+'.'+mass1[i+2].split('.')[0];
                customurl+= "helpmanager/get?class="+customurl3;

                $(div2).setAttribute("customHelpUrl", customurl2+"helpmanager/update");
                $(div2).setAttribute("className", customurl3);
            } 

            else {
                var mass1= this.getAttribute("helpURL").split('/');
                var i = 0;
                for (i = 0; i < mass1.length; i++) {
                    if(mass1[i] == 'descriptor')
                        break;
                }
                var customurl = "";

                for (var j = 0; j < i; j++) {
                    customurl += mass1[j]+"/";
                }
                var customurl2 = customurl;
                customurl+= "helpmanager/get?class="+mass1[i+1];

                $(div2).setAttribute("customHelpUrl", customurl2+"helpmanager/update");
                $(div2).setAttribute("className", mass1[i+1]);
            }

            new Ajax.Request(customurl, {
                method : 'get',
                onSuccess : function(x) {
                    var from = x.getResponseHeader("X-Plugin-From");
                    div2.innerHTML = x.responseText+(from?"<div class='from-plugin'>"+from+"</div>":"");
                    layoutUpdateCallback.call();
                },
                onFailure : function(x) {
                    div2.innerHTML = "<b>ERROR</b>: Failed to load help file: " + x.statusText;
                    layoutUpdateCallback.call();
                }
            });
        } else {
            var div = $(findFollowingTR(tr, "help-area")).down().next().down();
            div.style.display = "none";
            $(tr).remove();
            layoutUpdateCallback.call();
        }

        return false;
    };
    e.tabIndex = 9999; 
    e = null; 
});

function save_custom_help(myel) {
    $(myel).style.display = "none";
    $(myel).next().style.display = "block";
    var div = $(myel).up().next();
    div.innerHTML = div.down().value;
    
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp=new XMLHttpRequest();
    }
    else {
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.open("POST",$(div).getAttribute("customHelpUrl"),true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("class="+$(div).getAttribute("className")+"&textArea="+div.innerHTML);

    return false;
};

function edit_custom_help(myel) {
    $(myel).style.display = "none";
        $(myel).previous().style.display = "block";
        var div = $(myel).up().next();
        div.innerHTML = "<textarea name='textArea'>"+div.innerHTML+"</textarea>";
        div.down().focus();
        return false;
};