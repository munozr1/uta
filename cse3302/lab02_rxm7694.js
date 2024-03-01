// Rodrigo Munoz 
// ID: 1001847694
// 06/29/2023
//

let isMult = val => mult => val%mult;
/*
    * @description : Multiplies each integer in the original array by mult and returns a new array
    * @param array : Array<Int>
    * @param mult : integer
    * @return : Array<Int>
* */
let multiplesGenerator = (array,mult) => array.map(val => val*mult);
//let multiplesGenerator = (array,mult) => console.log('array: ',array, ' mult: ', mult);
/*
    * @description: Creates a new array with the squared value of each value in the original array
    * @param array : Array 
    * @return: Array
* */
let squaresGenerator = array => array.map(val => val*val);
/*
    * @description : Use modulo operator to determine even or odd
    * @param val : Integer
    * @return : 1 | 0
*/
let evenodd = val => val%2;

/*
   * @description : returns all even or odds in an array of integers
   * @param array : Array<Int>
   * @param mult : Integer
   * @param eo: 1 | 0
   * @return eo: Array<Int>
* */
let evenoddsFromArray = (array) => ( mult) => (eo) => multiplesGenerator(array,mult).filter(val => eo ? evenodd(val) : !evenodd(val));
let evenoddsSumFromArray= (array) => ( mult) => (eo) => multiplesGenerator(array,mult).filter(val => eo ? evenodd(val) : !evenodd(val))
.reduce((acc, val) => acc + val);

/*
    * @param f : function that takes 1 variable
    * @param g : function that takes 2 variable
    * @return : function that composes f and g -> f(g(x)(y))
*/
let compose = (f, g) => (x,y)=>  f(g(x,y));

/*
    * @description : Creates an array with numberts from start - end
    * @param start : Integer
    * @param end: Integer
    * @return : Array<Int>
* */
let arrayRange = (start,end) => Array.from({start:end, length: end-start+1}, (val, idx) => idx+start);
/*
    * @description : Creates an array with numbers of multiple MULT from 1-100 and then filters for even or odds
    * @param array: array from 1-100
    * @param mult: Integer
    * @param eo: 1 | 0
    * @return : Array<Int>
* */
let evenoddsFromRangeSum  = array => mult => eo => multiplesGenerator(array, mult).filter(val => val <= 100).filter(val => eo? !evenodd(val) : evenodd(val)).reduce((acc, val ) => acc + val);




inputtable = [1,2,3,4,5,6,7,8,9,10];


// Multiples of 5
//Set of multiples of 5 between 1 and 51
fivesTable = multiplesGenerator(inputtable,5);
console.log("fiveTable: ",fivesTable);

// Multiples of 13
//Set of multiples of 13 between 1 and 131
thirteensTable = multiplesGenerator(inputtable,13);
console.log("thirteenTable: ",thirteensTable)

// Squares of inputtable
//Set of squares of the numbers in inputtable
squaresTable = squaresGenerator(inputtable);
console.log("squaresTable: ", squaresTable);

//the odd multiples of 5 between 1 and 100. 5, 15, ...
oddsTable = evenoddsFromArray([1,2,3,4,5,6,7,8,9,10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20])(5)(1);
console.log("odd multiples of 5 from 1-100: ", oddsTable);

//Get (and then print) the sum of even multiples of 7 between 1 and 100.
// TA can reuse arr 
arr = arrayRange(1,100);
evensOfMult7Table = evenoddsFromRangeSum(arr)(7)(1)//.filter(val => val <= 100).filter(val => !evenodd(val)).reduce((acc, val ) => acc + val);
console.log("sum of even multiples of 7 from 1-100: ",evensOfMult7Table );


/*function cylinder_volume(r, h){ 
var volume = 3.14 * r * r * h; 
return volume; 
}
*/
cylinder_volume = r => h => 3.14*r*r*h;

console.log("r = 5, h = 10 => ", cylinder_volume(5)(10));
console.log("r = 5, h = 17 => ", cylinder_volume(5)(17));
console.log("r = 5, h = 11 => ", cylinder_volume(5)(11));



makeTag = function(beginTag, endTag){ 
  return function(textcontent){ 
      return beginTag +textcontent +endTag; 
  } 
}

table = ctx => makeTag("<table>","</table>")(ctx());
tr = ctx => makeTag("<tr>","</tr>")(ctx);
th= ctx => makeTag("<th>","</th>")(ctx);
td= makeTag("<td>","</td>");


newTable = table(() => tr(th("name") + td("Rodrigo")));
console.log(newTable);


