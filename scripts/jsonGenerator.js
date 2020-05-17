/*

{
  workshops: function() {

    let TOTAL_WORKSHOPS = 50;

    var workshops = [];
    var acronyms = [];

    var i = 0;
    while(i < TOTAL_WORKSHOPS) {

      var exists = false;
      var tmpAcronym = acronymGen();
      for(var j = 0; j < i; j++) {
        if(tmpAcronym.localeCompare(acronyms[j]) == 0) {
          exists = true;
          break;
        }
      }

      if(!exists) {
        workshops[i] = {
          acronym: tmpAcronym,
          price: priceGen(),
          category: categoryGen(),
          timetable: timetableGen(),
          rgbColor: rgbColorGen()
        };
        i++;
      }


    }

    return workshops;

    function acronymGen() {

      let CHAR_LIMIT = 3;
      let NUM_LIMIT = 2;
      let CHARS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
      let NUMS = '0123456789';


      var result = '';
      for(var i = 0; i < CHAR_LIMIT; i++) {
        result += CHARS.charAt(Math.floor(Math.random() * CHARS.length));
      }

      result += '-';
      for(i = 0; i < NUM_LIMIT; i++) {
        result += NUMS.charAt(Math.floor(Math.random() * NUMS.length));
      }

      return result;

    }

    function priceGen() {
      let PRICE_LIMIT = 100;
      return parseFloat((Math.random() * PRICE_LIMIT).toFixed(2).replace(",", "."));
    }

    function categoryGen() {
      let CATEGORY_LIMIT = 5;
      return Math.floor(Math.random() * CATEGORY_LIMIT) + 1;
    }

    function timetableGen() {

      let TIMES_LIMIT = 4;
      let DAY_LIMIT = 5;
      let HOUR_LIMIT = 12;

      var times = Math.floor(Math.random() * TIMES_LIMIT) + 1;
      var days = [];
      var hours = [];
      var dayHours = [];

      var i = 0;
      while(i < times) {

        days[i] = Math.floor(Math.random() * DAY_LIMIT);
        hours[i] = Math.floor(Math.random() * HOUR_LIMIT);

        var found = false;
        for(var j = 0; j < i; j++) {
          if(days[j] == days[i] && hours[j] == hours[i]) {
            found = true;
            break;
          }
        }

        if(!found) {
          dayHours[i] = {
            day: days[i],
            hour: hours[i]
          };
          i++;
        }

      }

      return dayHours;

    }

    function rgbColorGen() {
      let COLOR_LIMIT = 256;
      return [
        Math.floor(Math.random() * COLOR_LIMIT),
        Math.floor(Math.random() * COLOR_LIMIT),
        Math.floor(Math.random() * COLOR_LIMIT)
      ];
    }

  },
    compatibilityMatrix: function(tags) {

      var matrix = [];

      for(var i = 0; i < this.workshops.length; i++) {

        matrix[i] = [];

        for(var j = 0; j < this.workshops.length; j++) {
          if(i < j) {
            matrix[i][j] = Math.random() >= 0.5 ? 1 : 0;
          } else if(i == j) {
            matrix[i][j] = 1; 	
          } else {
            matrix[i][j] = matrix[j][i];
          }
        }

      }

      return matrix;

    }
}

*/