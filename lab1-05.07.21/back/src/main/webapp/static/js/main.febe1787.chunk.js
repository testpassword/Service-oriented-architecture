(this.webpackJsonpfront=this.webpackJsonpfront||[]).push([[0],{191:function(e,t,n){},192:function(e,t,n){},333:function(e,t,n){"use strict";n.r(t);var c,r=n(0),i=n.n(r),a=n(27),o=n.n(a),s=(n(191),n(52)),l=(n(192),n(335)),u=n(77),j=n(348),d=n(349),b=function(e){if(e.ok)return e.json();throw new Error(e.json())};!function(e){e.PERSONS="http://localhost:8080/back-1.0-ULTIMATE/api/persons/",e.DRAGONS="http://localhost:8080/back-1.0-ULTIMATE/api/dragons/"}(c||(c={}));var h=function(e){return fetch(e,{method:"GET"}).then(b)},O=function(e,t,n){return fetch("".concat(e).concat(t),{method:"PUT",headers:{"Content-Type":"application/json"},body:JSON.stringify(n)}).then(b)},f=function(e,t){return console.info(t),fetch(e,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({name:"test2",height:12,weight:13,passportID:"fgbkk",hairColor:"GREEN"})}).then(b)},p=function(e,t){return fetch("".concat(e).concat(t),{method:"DELETE"}).then(b)},g=n(130),m=n(49),x=n(341),y=n(342),S=n(51),k=n(179),v=n(339),E=n(343),w=n(183),N=n(336),C=n(146),T=n(126),I=n(91),R=n(344),D=n(345),L=n(346),A=n(101),_=n(347),G=n(338),P=n(123),F=n(340),J=n(337),K=n(57),M=n.n(K),V=n(10),Y=function(e,t){var n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:[],c=arguments.length>3&&void 0!==arguments[3]?arguments[3]:{};return Object(V.jsxs)(G.a,{onFinish:t,children:[" ",Object.keys(e).filter((function(e){return"id"!==e.toLowerCase()})).map((function(t){var r;return Object(V.jsx)(G.a.Item,{label:t.toLowerCase(),name:t,rules:[{required:n.includes(t)}],children:(r=e[t],"enum"===r.type?Object(V.jsx)(P.a.Group,{optionType:"button",defaultValue:c[t],options:e[t].vals}):"number"===r.type?Object(V.jsx)(F.a,{defaultValue:c[t]}):"date"===r.type?Object(V.jsx)(J.a,{format:"DD.MM.YYYY",defaultValue:M()()}):Object(V.jsx)(x.a,{defaultValue:c[t]}))})})),Object(V.jsx)(G.a.Item,{children:Object(V.jsx)(S.a,{type:"primary",htmlType:"submit",children:"Submit"})})]})},U=n(172),W=n.n(U),B=n(184),H=["editing","dataIndex","title","inputType","record","index","children"],q=function(e){var t=e.editing,n=e.dataIndex,c=e.title,r=e.inputType,i=(e.record,e.index,e.children),a=Object(B.a)(e,H);return Object(V.jsx)("td",Object(m.a)(Object(m.a)({},a),{},{children:t?Object(V.jsx)(G.a.Item,{name:n,style:{margin:0},rules:[{required:!0,message:"Please Input ".concat(c,"!")}],children:"number"===r?Object(V.jsx)(F.a,{}):Object(V.jsx)(x.a,{})}):i}))},z={WATER:"geekblue",AIR:"blue",FIRE:"volcano",UNKNOWN:"magenta"},Q=function(){return fetch("".concat(c.DRAGONS,"grouped_by_type"),{method:"GET"}).then(b)},X=function(e){return fetch("".concat(c.DRAGONS,"find_with_killer_weaker_then?killer_id=").concat(e),{method:"GET"}).then(b)},Z={GREEN:"green",YELLOW:"yellow",BROWN:"gold"},$=function(e){return fetch("".concat(c.PERSONS,"find_person_included_in_name?name=").concat(e),{method:"GET"}).then(b)},ee=function(e){var t=e.entity,n=e.template,c=function(e){return Object.keys(e).map((function(t){return{title:t.toUpperCase(),key:t,dataIndex:t,editable:"id"!==t,inputType:e[t].type,sorter:function(n,c){var r=n[t],i=c[t],a=e[t].type;return"number"===a?r-i:"string"===a?r.length-i.length:"boolean"===a?r&&i:"date"===a?new Date(r)-new Date(i):void 0}}})).filter((function(e){return void 0!==e&&null!==e}))}(n),i=Object(r.useState)([]),a=Object(s.a)(i,2),o=a[0],u=a[1],j=Object(r.useState)(!0),d=Object(s.a)(j,2),b=d[0],G=d[1],P=Object(r.useState)([]),F=Object(s.a)(P,2),J=F[0],K=F[1],M=Object(r.useState)(!1),U=Object(s.a)(M,2),B=U[0],H=U[1],Z=Object(r.useState)(""),ee=Object(s.a)(Z,2),te=ee[0],ne=ee[1],ce=Object(r.useState)(""),re=Object(s.a)(ce,2),ie=re[0],ae=re[1],oe=Object(r.useState)(""),se=Object(s.a)(oe,2),le=se[0],ue=se[1],je=function(e,t,n){t(),ne(e[0]),ae(n)},de=function(e){e(),ne("")};Object(r.useEffect)((function(){h(t).then((function(e){G(!1),u(e.map((function(e){return Object(m.a)(Object(m.a)({},e),{},{key:e.id})})))})).catch((function(e){return k.b.error(e)}))}),[t]);var be=[].concat(Object(g.a)(c.map((function(e){return Object(m.a)(Object(m.a)({},e),(t=e.dataIndex,{filterDropdown:function(e){var n=e.setSelectedKeys,c=e.selectedKeys,r=e.confirm,i=e.clearFilters;return Object(V.jsxs)("div",{children:[Object(V.jsx)(x.a,{placeholder:"Search ".concat(t),value:c[0],onChange:function(e){return n(e.target.value?[e.target.value]:[])},onPressEnter:function(){return je(c,r,t)}}),Object(V.jsxs)(y.b,{children:[Object(V.jsx)(S.a,{type:"primary",icon:Object(V.jsx)(T.a,{}),onClick:function(){return je(c,r,t)},children:"Search"}),Object(V.jsx)(S.a,{onClick:function(){return de(i)},children:"Reset"})]})]})},filterIcon:function(e){return Object(V.jsx)(T.a,{style:{color:e?"#1890ff":void 0}})},onFilter:function(e,n){return n[t]?n[t].toString().toLowerCase().includes(e.toLowerCase()):""},render:function(e){var n;return ie===t?Object(V.jsx)(W.a,{highlightStyle:{backgroundColor:"#ffc069",padding:0},searchWords:[te],autoEscape:!0,textToHighlight:null!==(n=null===e||void 0===e?void 0:e.toString())&&void 0!==n?n:""}):e}}));var t}))),[{title:"ACTIONS",dataIndex:"actions",render:function(e,c){return Object(V.jsx)(v.a.Link,{children:Object(V.jsx)(E.a,{content:Y(n,(function(e){var n=JSON.parse(JSON.stringify(e));0!==Object.keys(n).length?O(t,c.id,n).then((function(e){u([].concat(Object(g.a)(o.filter((function(e){return e.id!==c.id}))),[Object(m.a)(Object(m.a)({},c),n)])),k.b.success(e.msg)})).catch((function(e){return k.b.error(e.message)})):k.b.warning("Nothing to modify")}),[],c),children:"Edit"})})}}]),he=[Object(V.jsx)(E.a,{content:Object(V.jsxs)("div",{children:[Y(n,(function(e){f(t,e).then((function(t){u([].concat(Object(g.a)(o),[Object(m.a)(Object(m.a)({},e),{},{id:t.id})])),H(!1)})).catch((function(e){return k.b.error(e)}))})),Object(V.jsx)(S.a,{shape:"round",danger:!0,icon:Object(V.jsx)(I.a,{}),size:"small",onClick:function(){return H(!1)}})]}),visible:B,children:Object(V.jsx)(S.a,{type:"primary",icon:Object(V.jsx)(R.a,{}),ghost:!0,onClick:function(){return H(!0)},children:"Add record"})}),Object(V.jsx)(S.a,{icon:Object(V.jsx)(D.a,{}),ghost:!0,danger:!0,onClick:function(){0===J.length?k.b.warning("Nothing is selected"):Promise.all(J.map((function(e){return p(t,e)}))).then((function(){u(o.filter((function(e){return!J.includes(e.id)}))),k.b.success("All entities was deleted")}))},children:"Remove"})];switch(t.split("/").slice(-2)[0]){case"dragons":he.push(Object(V.jsx)(E.a,{trigger:"hover",onVisibleChange:function(e){e&&Q().then((function(e){return ue(e)}))},content:Object.keys(le).map((function(e){return Object(V.jsxs)(w.a,{color:z[e],children:[e," = ",le[e]]})})),title:"Count dragons by type",children:Object(V.jsx)(S.a,{icon:Object(V.jsx)(L.a,{}),ghost:!0,children:"Statistic"})})),he.push(Object(V.jsx)(E.a,{trigger:"click",content:Object(V.jsx)(x.a.Search,{onSearch:function(e){isNaN(e)?k.b.error("You should write id number of killer"):X(e).then((function(t){0!==t.length?u(t):k.b.warning("There are no dragons with a killer weaker then killer#".concat(e))}))},enterButton:!0,placeholder:"id"}),children:Object(V.jsx)(S.a,{icon:Object(V.jsx)(A.a,{}),ghost:!0,children:"Find with killer weaker then"})}));break;case"persons":he.push(Object(V.jsx)(E.a,{trigger:"click",content:Object(V.jsx)(x.a.Search,{onSearch:function(e){$(e).then((function(t){0!==t.length?u(t):k.b.warning("There are no persons included in name ".concat(e))}))},enterButton:!0,placeholder:"name"}),children:Object(V.jsx)(S.a,{icon:Object(V.jsx)(_.a,{}),ghost:!0,children:"Find person included in name"})}))}return Object(V.jsxs)(l.a,{className:"site-layout",children:[Object(V.jsx)(C.Header,{children:Object(V.jsx)(y.b,{children:he})}),Object(V.jsx)(C.Content,{children:Object(V.jsx)(N.a,{columns:be,rowSelection:{selectedRowKeys:J,onChange:function(e){return K(e)},selections:[N.a.SELECTION_ALL,N.a.SELECTION_INVERT,N.a.SELECTION_NONE]},dataSource:o,loading:b,components:{body:{cell:q}},pagination:{position:["bottomCenter"]}})})]})};ee.defaultProps={enumFields:new Map};var te=ee,ne=function(){var e=Object(V.jsx)(te,{entity:c.PERSONS,template:{id:{type:"number"},name:{type:"string"},height:{type:"number"},weight:{type:"number"},passportID:{type:"string"},hairColor:{type:"enum",vals:Object.keys(Z)}}}),t=Object(V.jsx)(te,{entity:c.DRAGONS,template:{id:{type:"number"},name:{type:"string"},creationDate:{type:"date"},age:{type:"number"},wingspan:{type:"number"},color:{type:"enum",vals:Object.keys(Z)},type:{type:"enum",vals:Object.keys(z)},killerID:{type:"number"}}}),n=Object(r.useState)(e),i=Object(s.a)(n,2),a=i[0],o=i[1],b=Object(r.useState)(!1),h=Object(s.a)(b,2),O=h[0],f=h[1];return Object(V.jsxs)(l.a,{style:{minHeight:"100vh"},children:[Object(V.jsx)(l.a.Sider,{collapsible:!0,collapsed:O,onCollapse:function(){return f(!O)},children:Object(V.jsxs)(u.a,{defaultSelectedKeys:["1"],mode:"inline",children:[Object(V.jsx)(u.a.Item,{icon:Object(V.jsx)(j.a,{}),onClick:function(){return o(e)},children:"Persons"}),Object(V.jsx)(u.a.Item,{icon:Object(V.jsx)(d.a,{}),onClick:function(){return o(t)},children:"Dragons"})]})}),Object(V.jsxs)(l.a,{className:"site-layout",children:[Object(V.jsx)(l.a.Content,{children:Object(V.jsx)("div",{className:"site-layout-background",style:{minHeight:360},children:a})}),Object(V.jsx)(l.a.Footer,{style:{textAlign:"center"},children:Object(V.jsx)("a",{href:"https://se.ifmo.ru/~s265570/cv/",children:"Kulbako Artemy 2021"})})]})]})};o.a.render(Object(V.jsx)(i.a.StrictMode,{children:Object(V.jsx)(ne,{})}),document.getElementById("root"))}},[[333,1,2]]]);
//# sourceMappingURL=main.febe1787.chunk.js.map