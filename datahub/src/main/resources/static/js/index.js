if (typeof jQuery === 'undefined') {
  throw new Error('emaliCase requires jQuery');
}
$.emailCase = {};
(function($, emailCase) {
  // 初始化页面数据
  emailCase.init = {
    fixedWidth: $('.fixedWidth'),
    cityCont: '至少选择一个城市'
  }
  emailCase.initDate = function(type, data, detail) {
    var arr = []
    if (type === 7) {
      if (detail === 'filterRemoveCity') {
        $('.listCitySet').addClass('disabled')
      } else {
        $('[name="' + detail + '"]').attr('disabled', 'disabled')
      }
    } else if (type === 5) {} else if (type === 2) {
      $('[name="' + detail + '"]').attr('disabled', 'disabled')
      $('[name="' + detail + 'End"]').attr('disabled', 'disabled')
    } else {
      $('[name="' + detail + '"]').attr('disabled', 'disabled')
    }
    if (data) {
      arr = data;
      if (type === 1) {
        $('input[name="' + detail + '"]').val(arr.split(',')[1]);
        if (arr.split(',')[0] === '1') {
          $('.' + detail).iCheck('check');
          $('[name="' + detail + '"]').removeAttr('disabled')
        } else {
        	if (detail === 'filterMaxMoney') {
         	   $('.slideUp').slideUp()
               }
        	if (detail === 'filterTodayMaxMoney') {
         	   $('.daySlideUp').slideUp()
            }
        }
      } else if (type === 2) {
        $('input[name="' + detail + '"]').val(arr.split(',')[0]);
        $('input[name="' + detail + 'End"]').val(arr.split(',')[1]);
        $('.' + detail).iCheck('check');
        $('[name="' + detail + '"]').removeAttr('disabled')
        $('[name="' + detail + 'End"]').removeAttr('disabled')
      } else if (type === 6) {
        $('.dateTypeYes').iCheck('check');
        $('[name="' + detail + '"]').removeAttr('disabled')
      } else if (type === 7) {
        if (detail === 'filterRemoveCity') {
          $('.listCitySet').removeClass('disabled')
        } else {
          $('[name="' + detail + '"]').removeAttr('disabled')
        }
        $('.' + detail).iCheck('check');
      }
    } else {
      if (type === 6) {
        $('.dateTypeNo').iCheck('check');
      } else if (type === 7) {
        if (detail === 'filterRemoveCity') {
          $('.listCitySet').addClass('disabled')
        }
      }
    }
  }
  emailCase.inputIfChecked = function ($this) {
	  var detail = $this.context.className
      if (detail === 'dateTypeYes') {
   	   $('[name="sendFrequencyDate"]').removeAttr('disabled')
      } else if (detail === 'dateTypeNo') {
   	   
      } else if (detail === 'sendHostCardCode') {
      } else if (detail === 'filterPriorityCity') {
   	   $('[name="' + detail +'"]').removeAttr('disabled')
      } else if (detail === 'filterRemoveCity') {
   	   $('.listCitySet').removeClass('disabled')
      } else {
   	   $('[name="' + detail + '"]').removeAttr('disabled')
   	   $('[name="' + detail + 'End"]').removeAttr('disabled')
      }
      if (detail === 'filterMaxMoney') {
   	   $('.slideUp').slideDown()
      }
      if (detail === 'filterTodayMaxMoney') {
   	   $('.daySlideUp').slideDown()
      }
  }
  emailCase.inputIfUnchecked = function ($this) {
	  var detail = $this.context.className
  	$this.closest('.form-group').find('i.error').remove()
  	if (detail === 'dateTypeYes') {
  	   $('[name="sendFrequencyDate"]').attr('disabled', 'disabled')
     } else if (detail === 'dateTypeNo') {
  	   
     } else if (detail === 'sendHostCardCode') {
  	   
     } else if (detail === 'filterPriorityCity') {
  	   $('[name="' + detail +'"]').attr('disabled', 'disabled')
     } else if (detail === 'filterRemoveCity') {
  	   $('.listCitySet').addClass('disabled')
     } else {
  	   $('[name="' + detail + '"]').attr('disabled', 'disabled')
  	   $('[name="' + detail + 'End"]').attr('disabled', 'disabled')
     }
  	if (detail === 'filterMaxMoney') {
   	   $('.slideUp').slideUp()
      }
  	if (detail === 'filterTodayMaxMoney') {
    	   $('.daySlideUp').slideUp()
       }
  }
  emailCase.editContent = function (type) {
		if (type === 'edit') {
			$('ol.annex li').each(function(i, v) {
	    		liArr.push($(v).attr('data-name'))
			})
			
			$('.annex-all .annex-edit0').each(function(i, v) {
				divArr.push($(v).attr('data-name'))
			})
		} else if (type === "submit") {
			$('.annex-common input:checked').each(function (i, v) {
				 liArr.push($(v).attr('class'))
			 })
		    $('.annex-common input[type="checkbox"]').not(':checked').each(function(i, v) {
					divArr.push($(v).attr('class'))
				})
		} else {}
		commonArr = liArr.concat(divArr)
		
    	for (var i in commonArr) {
    		if (commonArr[i] === 'sendHostCardCode') {
    			annexEdit[i] = annex_html_1
    		} else if (commonArr[i] === 'sendTime') {
    			annexEdit[i] = annex_html_2
    		} else if (commonArr[i] === 'sendDate') {
    			annexEdit[i] = annex_html_3
    		} else if (commonArr[i] === 'sendCompanyBox') {
    			annexEdit[i] = annex_html_4
    		} else if (commonArr[i] === 'sendAgencyCode') {
    			annexEdit[i] = annex_html_5
    		} else {
    			
    		}
    	}
 }
  emailCase.inputIfUnchecked = function ($this) {
	  var detail = $this.context.className
    	$this.closest('.form-group').find('i.error').remove()
    	if (detail === 'dateTypeYes') {
    	   $('[name="sendFrequencyDate"]').attr('disabled', 'disabled')
       } else if (detail === 'dateTypeNo') {
    	   
       } else if (detail === 'sendHostCardCode') {
    	   
       } else if (detail === 'filterPriorityCity') {
    	   $('[name="' + detail +'"]').attr('disabled', 'disabled')
       } else if (detail === 'filterRemoveCity') {
    	   $('.listCitySet').addClass('disabled')
       } else {
    	   $('[name="' + detail + '"]').attr('disabled', 'disabled')
    	   $('[name="' + detail + 'End"]').attr('disabled', 'disabled')
       }
    	if (detail === 'filterMaxMoney') {
     	   $('.slideUp').slideUp()
        }
    	if (detail === 'filterTodayMaxMoney') {
      	   $('.daySlideUp').slideUp()
         }
  }
  // 初始化城市格式
  emailCase.initCityFormat = function(city) {
    var cityObj = [];
    cityObj = [{
      p: "不限",
      c: [{
        ct: "不限",
        d: ["不限"]
      }]
    }]
    for (var i in city) {
      var citys = [{
        ct: "不限",
        d: ["不限"]
      }];
      for (var j in city[i]) {
        city[i][j].unshift('不限')
        citys.push({
          ct: j,
          d: city[i][j]
        })
      }
      cityObj.push({
        p: i,
        c: citys
      })
    }
    return cityObj;
  }
  // 初始化城市
  emailCase.getCity = function(cityType) {
    var cityStr = ''
    var removeCity = []
    $(cityType).find('.selectList').each(function() {
      var province = $(this).find(".province").val();
      var city = $(this).find(".city").val();
      var district = $(this).find(".district").val();
      cityStr = province + ',' + city + ',' + district
      removeCity.push(cityStr);
    })
    return removeCity.join(';')
  }
  // 剔除城市
  emailCase.getFilterCity = function(cityType) {
    var str = '';
    $(cityType + ' span').each(function(i, v) {
      str += $(v).text() + ';'
    })
    str = str.replace(/-/ig, ',');
    str = str.substring(0, str.length - 1);
    return str
  }
  // 初始化城市展示
  emailCase.initCityShow = function(typeArr, tree, _html) {
    var initHtml = ''
    if (typeArr.join() !== '') {
      if (typeArr.length > 1) {
        typeArr.forEach(function() {
          initHtml += _html
        })
      } else if (typeArr.length === 1) {
        initHtml += _html

      } else {}
      $(tree).html(initHtml)
    }
  }
  emailCase.initFilterHtml = function(cityProvince, cityHtml, $city) {
    var provinceHtml = '<div class="row">';
    for (var s in $city) {
      provinceHtml += '<div class="col-md-2"><a href="javascript:void(0);" class="province' + s + '" title="' + $city[s].p + '">' + $city[s].p + '</a><i class="next glyphicon glyphicon-chevron-right"></i></div>'
    }
    provinceHtml += '</div>';
    var provinceHtmlInit = '';
    cityProvince.html(provinceHtml);
    // 判断cityHtml是否等于‘’
    var cityLen = $('.' + cityHtml + ' > div');
    var provinceClass = cityLen.attr('class');
    if ($('.' + cityHtml).html() !== '') {
      // 初始化市 ,选中状态
      for (var i in cityLen) {
        if (cityLen[i].className) {
          cityProvince.find('.' + cityLen[i].className).addClass('focus');
        }
      }
      cityProvince.find('.' + provinceClass).addClass('focus');
    }
  }
  emailCase.initFilterCityShow = function(typeArr, tree, $city) {
    // 单个城市
    var provClass;
    var cityClass;
    var distClass;
    var provText;
    var cityText;
    var distText;
    var cityHtml = '';
    for (var i in typeArr) {
      for (var j in $city) {
        if ($city[j].p === typeArr[i].split(',')[0]) {
          provClass = 'province' + j;
          provText = typeArr[i].split(',')[0];
          for (var k in $city[j].c) {
            if ($city[j].c[k].ct === typeArr[i].split(',')[1]) {
              cityClass = 'city' + k;
              cityText = typeArr[i].split(',')[1];
              for (var l in $city[j].c[k].d) {
                if ($city[j].c[k].d[l] === typeArr[i].split(',')[2]) {
                  distClass = 'dist' + l;
                  distText = typeArr[i].split(',')[2];
                  if ($(tree + '> div').hasClass(provClass)) {
                    if ($(tree + ' > div > div').hasClass(cityClass)) {
                      $(tree + ' .' + provClass + ' .' + cityClass).append('<span class="label label-info"><i class="prov">' + provText + '</i>-<i class="city">' + cityText + '</i>-<i class="dist ' + distClass + '">' + distText + '</i></span>');
                    } else {
                      $(tree + ' .' + provClass).append('<div class="' + cityClass + '"><span class="label label-info"><i class="prov">' + provText + '</i>-<i class="city">' + cityText + '</i>-<i class="dist ' + distClass + '">' + distText + '</i></span></div>');
                    }
                  } else {
                    $(tree).append('<div class="' + provClass + '"><div class="' + cityClass + '"><span class="label label-info"><i class="prov">' + provText + '</i>-<i class="city">' + cityText + '</i>-<i class="dist ' + distClass + '">' + distText + '</i></span></div></div>');
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  // 城市初始展现
  emailCase.initPCD = function(optionAll, index, initOptionCity) {
    if (optionAll !== undefined) {
      optionAll.each(function() {
        if ($(this).val() === initOptionCity.split(',')[index]) {
          $(this).attr('selected', 'selected')
        }
      })
    }
  }
  // 获取城市
  emailCase.selectCity = function(_this, oProvince, oCity, oDistrict, areaJson) {
    //初始化省 
    var temp_html = '';
    var province = function() {
      $.each(areaJson, function(i, province) {
        temp_html += '<option value="' + province.p + '">' + province.p + "</option>";
      });
      oProvince.html(temp_html);
      city();
    };
    //赋值市 
    var city = function() {
      temp_html = "";
      var n = oProvince.get(0).selectedIndex;
      $.each(areaJson[n].c, function(i, city) {
        temp_html += '<option value="' + city.ct + '">' + city.ct + '</option>';
      });
      oCity.html(temp_html);
      district();
    };
    //赋值县 
    var district = function() {
      temp_html = "";
      var m = oProvince.get(0).selectedIndex;
      var n = oCity.get(0).selectedIndex;
      if (typeof(areaJson[m].c[n].d) == "undefined") {
        oDistrict.css("display", "none");
      } else {
        oDistrict.css("display", "inline");
        $.each(areaJson[m].c[n].d, function(i, district) {
          temp_html += '<option value="' + district + '">' + district + '</option>';
        });
        oDistrict.html(temp_html);
      };
    };
    //选择省改变市 
    oProvince.change(function() {
      city();
    });
    //选择市改变县 
    oCity.change(function() {
      district();
    });
    province();
  }

  // 提交数据

  // 复选框的数据
  emailCase.filterCommonAll = function(type, detail) {
    var data = '';
    var commonVal = $('input[name="' + detail + '"]').val();
    if (type === 1) {
      if (commonVal === '') {
        commonVal = 0;
      }
      if ($('.' + detail).is(':checked')) {
        data = '1,' + commonVal;
      } else {
        data = '0,' + commonVal;
      }
    } else if (type === 2) {
      if ($('.' + detail).is(':checked')) {
        data = commonVal + ',' + $('input[name="' + detail + 'End"]').val();
      }
    } else if (type === 3) {
      if ($('.' + detail).is(':checked')) {
        data = '1,' + commonVal
      } else {
        data = '0,' + commonVal
      }
    } else if (type === 4) {
      if ($('.' + detail).is(':checked')) {
        data = '1,' + $('select[name="' + detail + '"]').val()
      } else {
        data = '0,' + $('select[name="' + detail + '"]').val()
      }
    } else if (type === 5) {
      if ($('.' + detail).is(':checked')) {
        data = '1'
      } else {
        data = '0'
      }
    } else if (type === 6) {
      if ($('.dateTypeYes').is(':checked')) {
        data = commonVal
      }
    } else if (type === 7) {
      if ($('.' + detail).is(':checked')) {
        data = $.emailCase.getFilterCity('#removeCity')
      }
    } else if (type === 8) {
      if ($('.' + detail).is(':checked')) {
        data = $.emailCase.getCity('#chooseCity')
      }
    } else if (type === 9) {
      if ($('.' + detail).is(':checked')) {
        data = '1'
      }
    } else if (type === 10) {
      if ($('.' + detail).is(':checked')) {
        data = commonVal
      }
    } else if (type === 11) {
      data = commonVal
    } else if (type === 12) {
      data = $('select[name="' + detail + '"]').val().trim()
    } else if (type === 13) {
      data = $('textarea[name="' + detail + '"]').val().trim()
    }
    return data
  }

  // 验证是否为空,填写是否正确

  emailCase.validTotal = function() {
    var cityCont = $.emailCase.init.cityCont
    var fixedWidth = $.emailCase.init.fixedWidth
    var tip = 0;

    function ifElse(_this, content) {
      tip++;
      if (_this.closest('label').children().hasClass('error')) {
        _this.parent().find('i.error').html(content)
      } else {
        _this.parent('label').append('<i class="error">' + content + '</i>')
      }
    }
    // 邮箱附件必须包含的字段
    var ifCheck = 0
    fixedWidth.find('input[type="checkbox"]').each(function(i, v) {
      var _this = $(this);
      if (!(_this.is(':checked'))) {
        ifCheck++;
      }
    })
    if (ifCheck === 5) {
      tip++;
      if (fixedWidth.children().hasClass('error')) {
        fixedWidth.find('i.error').html('必须选择一项')
      } else {
        fixedWidth.append('<i class="error">' + '必须选择一项' + '</i>')
      }
    } else {
      fixedWidth.find('i.error').remove()
    }
    // 验证输入是否正确
    function validEmail(_this, emailVal, regType) {
      for (var i = 0; i < emailVal.length; i++) {
        if (regType.test(emailVal[i])) {
          _this.parent().find('i.error').remove()
        } else {
          ifElse(_this, '格式错误');
          break;
        }
      }
    }

    function validCommon(_this, type, val) {
      var emailVal;
      var regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      if (type === 1) {
        if (val.trim() === '') {
          ifElse(_this, '不能为空')
        } else {
          _this.parent().find('i.error').remove()
        }
      } else if (type === 2) {
        if (val.trim() === '') {
          ifElse(_this, '不能为空');
        } else {
          emailVal = val.replace('，', ',').split(',');
          validEmail(_this, emailVal, regEmail)
        }
      } else if (type === 3) {
        if (val.trim() === '') {
          ifElse(_this, '不能为空');
        } else {
          emailVal = val.split();
          validEmail(_this, emailVal, regEmail)
        }
      } else if (type === 4) {
        if (val.trim() !== '') {
          emailVal = val.replace('，', ',').split(',');
          validEmail(_this, emailVal, regEmail)
        } else {
          _this.parent().find('i.error').remove()
        }
      } else {}
    }

    // 验证必填不为空的情况
    $('.validRequired input.validEmpty').each(function(i, v) {
      var _this = $(this)
      validCommon(_this, 1, $(v).val());
    })
    // 验证必填且要验证email，emailList
    $('.validRequired input.validEmail').each(function(i, v) {
      var _this = $(this);
      validCommon(_this, 2, $(v).val());

    })
    $('.validRequired input.validEmailSingal').each(function(i, v) {
      var _this = $(this);
      validCommon(_this, 3, $(v).val());

    })
    // 可填可不填的，验证输入是否正确validEmailEmpty
    $('input.validEmailEmpty').each(function(i, v) {
      var _this = $(this);
      validCommon(_this, 4, $(v).val());
    })
    // 选中之后验证格式是否正确 日期
    if ($('.dateTypeYes').is(':checked')) {
      var _this = $('input[name="sendFrequencyDate"]');
      var val = _this.val()
      if (val.trim() === '') {
        ifElse(_this, '不能为空')
      } else {
        var regNumber = /^((0?[1-9])|((1|2)[0-9])|30|31)$/;
        var emailVal = val.replace('，', ',').split(',');
        validEmail(_this, emailVal, regNumber)
      }
    } else {
      $('input[name="sendFrequencyDate"]').parent().find('i.error').remove()
    }


    // 验证checkbox
    $('.validCheckbox input[type="checkbox"], .validCheckbox input[type="radio"]').each(function(i, v) {
      var _this = $(this)
      if (_this.is(':checked')) {
        var __this = _this.closest('.validCheckbox').find('input[type=text]');
        var boxVal = __this.val();
        if (boxVal.trim() === '') {
          ifElse(__this, '不能为空')
        } else {
          __this.parent().find('i.error').remove()
        }
      } else {
        _this.closest('.validCheckbox').find('i.error').remove()
      }
    })
    // 公共class,each
    // sendCompanyBox sendAgencyCode filterRemoveArea filterDelegateCode filterPriorityCity filterCaseCity
    function validCity(_this) {
      if (_this.html() === '') {
        tip++
        if (_this.parent().hasClass('error')) {
          _this.parent().find('.removeCity').find('i.error').html(cityCont)
        } else {
          _this.parent.find('.removeCity').append('<i class="error">' + cityCont + '</i>')
        }
      } else if ($('#removeCity span').text().split('-')[0] === '不限') {
        tip++
        if (_this.parent().hasClass('error')) {
          _this.parent().find('.removeCity').find('i.error').html(cityCont)
        } else {
          _this.parent().find('.removeCity').append('<i class="error">' + cityCont + '</i>')
        }
      } else {
        _this.parent().find('i.error').remove()
      }
    }

    function validCity(_this) {
      if (_this.html() === '') {
        tip++
        if (_this.hasClass('error')) {
          _this.find('i.error').html(cityCont)
        } else {
          _this.append('<i class="error">' + cityCont + '</i>')
        }
      } else {
        _this.find('i.error').remove()
      }
    }
    if ($('.filterRemoveCity').is(':checked')) {
      var _this = $('#removeCity');
      validCity(_this);
    }

    if ($('.filterPriorityArea').is(':checked')) {
      var _this = $('#chooseCity');
      validCity(_this);
    }

    function isInteger(obj) {
      return obj % 1 === 0
    }

    function validMoneyInteger(_this, _thisEnd, start, end) {
      if (start.val() === '') {
        start.val('')
        ifElse(start, '不能为空')
      } else if (start.val() < 0 || !isInteger(start.val()) || typeof(start.val()) === 'number') {
        tip++;
        if (_this.children().hasClass('error')) {
          _this.find('i.error').html('格式错误')
        } else {
          _this.append('<i class="error">格式错误</i>')
        }
      } else {
        _this.find('i.error').remove()
      }

      if (end.val() === '') {
        end.val('')
        ifElse(end, '不能为空')
      } else if (end.val() < 0 || !isInteger(end.val())) {
        tip++;
        if (_thisEnd.children().hasClass('error')) {
          _thisEnd.find('i.error').html('格式错误')
        } else {
          _thisEnd.append('<i class="error">格式错误</i>')
        }
      } else {
        _thisEnd.find('i.error').remove()
      }

      if (start.val() > 0 && end.val() > 0) {
        if (start.val() - end.val() > 0) {
          tip++;
          if (_this.children().hasClass('error')) {
            _this.find('i.error').html('前面的数不能大于后面的数')
          } else {
            _this.append('<i class="error">前面的数不能大于后面的数</i>')
          }
        }
      }
    }

    function validMoney(_this, _thisEnd, start, end) {
      if (start.val() === '') {
        start.val('')
        ifElse(start, '不能为空')
      } else if (start.val() < 0 || typeof(start.val()) === 'number') {
        tip++;
        if (_this.children().hasClass('error')) {
          _this.find('i.error').html('格式错误')
        } else {
          _this.append('<i class="error">格式错误</i>')
        }
      } else {
        _this.find('i.error').remove()
      }

      if (end.val() === '') {
        end.val('')
        ifElse(end, '不能为空')
      } else if (end.val() < 0) {
        tip++;
        if (_thisEnd.children().hasClass('error')) {
          _thisEnd.find('i.error').html('格式错误')
        } else {
          _thisEnd.append('<i class="error">格式错误</i>')
        }
      } else {
        _thisEnd.find('i.error').remove()
      }

      if (start.val() > 0 && end.val() > 0) {
        if (start.val() - end.val() > 0) {
          tip++;
          if (_this.children().hasClass('error')) {
            _this.find('i.error').html('前面的数不能大于后面的数')
          } else {
            _this.append('<i class="error">前面的数不能大于后面的数</i>')
          }
        }
      }
    }

    if ($('.filterCaseMoney').is(':checked')) {
      var start = $('input[name="filterCaseMoney"]');
      var end = $('input[name="filterCaseMoneyEnd"]');
      var _this = start.closest('label');
      var _thisEnd = end.closest('label');
      validMoney(_this, _thisEnd, start, end);
    }
    if ($('.filterPrincipalYuan').is(':checked')) {
      var start = $('input[name="filterPrincipalYuan"]');
      var end = $('input[name="filterPrincipalYuanEnd"]');
      var _this = start.closest('label');
      var _thisEnd = end.closest('label');
      validMoney(_this, _thisEnd, start, end);
    }

    if ($('.filterCaseAge').is(':checked')) {
      var start = $('input[name="filterCaseAge"]');
      var end = $('input[name="filterCaseAgeEnd"]');
      var _this = start.closest('label');
      var _thisEnd = end.closest('label');
      validMoneyInteger(_this, _thisEnd, start, end);
      if (start.val() > 100 || end.val() > 100) {
        tip++;
        if (_this.children().hasClass('error')) {
          _this.find('i.error').html('格式错误，请输入1~100之间的数')
        } else {
          _this.append('<i class="error">格式错误，请输入1~100之间的数</i>')
        }
      }
    }
    if ($('.filterCaseYear').is(':checked')) {
      var start = $('input[name="filterCaseYear"]');
      var end = $('input[name="filterCaseYearEnd"]');
      var _this = start.closest('label');
      var _thisEnd = end.closest('label');
      validMoneyInteger(_this, _thisEnd, start, end)
      if (start.val() > 2010 || end.val() > 2010 || start.val() < 1900 || end.val() < 1900) {
        tip++;
        if (_this.children().hasClass('error')) {
          _this.find('i.error').html('格式错误，请输入1900~2010之间的数')
        } else {
          _this.append('<i class="error">格式错误，请输入1900~2010之间的数</i>')
        }
      }
    }
    return tip
  }
  emailCase.commonCity = function(type, _this, i, $city) {
    var oProvince = _this.find(".province");
    var oCity = _this.find(".city");
    var oDistrict = _this.find(".district");
    var cityObj = $city;
    $.emailCase.selectCity(_this, oProvince, oCity, oDistrict, cityObj)
    var optionsP = oProvince.find('option');
    $.emailCase.initPCD(optionsP, 0, type[i]);
    cityObj.forEach(function(value, index) {
      temp_html = "";
      if (value.p === type[i].split(',')[0]) {
        var n = oProvince.get(0).selectedIndex;
        $.each(cityObj[n].c, function(i, city) {
          temp_html += '<option value="' + city.ct + '">' + city.ct + '</option>';
        });
        oCity.html(temp_html);
        var optionsC = oCity.find('option');
        $.emailCase.initPCD(optionsC, 1, type[i]);
        temp_html = "";
        value.c.forEach(function(val, idx) {
          if (val.ct === type[i].split(',')[1]) {
            $.each(val.d, function(i, city) {
              temp_html += '<option value="' + city + '">' + city + '</option>';
            });
            oDistrict.html(temp_html);
            var optionsD = oDistrict.find('option');
            $.emailCase.initPCD(optionsD, 2, type[i]);
          }
        })
      }
    })
  }
})(jQuery, $.emailCase)
