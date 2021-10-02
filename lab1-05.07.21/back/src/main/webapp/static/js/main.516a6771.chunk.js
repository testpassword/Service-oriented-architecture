(this.webpackJsonpfront=this.webpackJsonpfront||[]).push([[0],{190:function(e,t,n){},191:function(e,t,n){},331:function(e,t,n){"use strict";n.r(t);var c,r=n(0),i=n.n(r),a=n(26),o=n.n(a),s=(n(190),n(49)),l=(n(191),n(335)),u=n(73),j=n(344),d=n(345),b=function(e){return e.json()};!function(e){e.PERSONS="http://localhost:8080/api/persons/",e.DRAGONS="http://localhost:8080/api/dragons/"}(c||(c={}));var O=function(e){return fetch(e,{method:"GET"}).then(b)},h=function(e,t){return fetch(e,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(t)}).then(b)},f=function(e,t){return fetch("".concat(e).concat(t),{method:"DELETE"}).then(b)},p=n(141),m=n.n(p),x=n(169),g=n(50),y=n(99),S=n(337),v=n(340),C=n(341),k=n(46),E=n(179),I=n(333),N=n(338),w=n(334),T=n(336),R=n(143),L=n(123),F=n(124),A=n(342),P=n(343),D=n(120),W=n(339),G=n(11),K=function(e,t){var n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:new Map,c=arguments.length>3&&void 0!==arguments[3]?arguments[3]:[];return Object(G.jsxs)(S.a,{onFinish:t,children:[Object.keys(e).filter((function(e){return"id"!==e.toLowerCase()})).map((function(t){return Object(G.jsx)(S.a.Item,{label:t.toLowerCase(),name:t,rules:[{required:c.includes(t)}],children:Object(y.a)(n.keys()).includes(t)?Object(G.jsx)(D.a.Group,{optionType:"button",options:n.get(t)}):"number"===e[t]?Object(G.jsx)(W.a,{}):Object(G.jsx)(v.a,{})})})),Object(G.jsx)(S.a.Item,{children:Object(G.jsx)(k.a,{type:"primary",htmlType:"submit",children:"Submit"})})]})},M=n(172),B=n.n(M),H=n(183),_=["editing","dataIndex","title","inputType","record","index","children"],z=function(e){var t=e.editing,n=e.dataIndex,c=e.title,r=e.inputType,i=(e.record,e.index,e.children),a=Object(H.a)(e,_);return Object(G.jsx)("td",Object(g.a)(Object(g.a)({},a),{},{children:t?Object(G.jsx)(S.a.Item,{name:n,style:{margin:0},rules:[{required:!0,message:"Please Input ".concat(c,"!")}],children:"number"===r?Object(G.jsx)(W.a,{}):Object(G.jsx)(v.a,{})}):i}))},J=function(e){var t=e.entity,n=e.template,c=e.enumFields,i=function(e){return Object.keys(e).map((function(t){return{title:t.toUpperCase(),key:t,dataIndex:t,editable:"id"!==t,inputType:e[t],sorter:function(e,n){var c=e[t],r=n[t],i=function(e){return[c,r].every((function(t){return typeof t===e}))};return i("number")?c-r:i("string")?c.length-r.length:i("boolean")?c&&r:void 0}}})).filter((function(e){return void 0!==e&&null!==e}))}(n),a=S.a.useForm(),o=Object(s.a)(a,1)[0],u=Object(r.useState)([]),j=Object(s.a)(u,2),d=j[0],b=j[1],p=Object(r.useState)(!0),D=Object(s.a)(p,2),W=D[0],M=D[1],H=Object(r.useState)(0),_=Object(s.a)(H,2),J=_[0],q=_[1],U=Object(r.useState)([]),V=Object(s.a)(U,2),Y=V[0],Q=V[1],X=Object(r.useState)(!1),Z=Object(s.a)(X,2),$=Z[0],ee=Z[1],te=Object(r.useState)(""),ne=Object(s.a)(te,2),ce=ne[0],re=ne[1],ie=Object(r.useState)(""),ae=Object(s.a)(ie,2),oe=ae[0],se=ae[1],le=function(e,t,n){t(),re(e[0]),se(n)},ue=function(e){e(),re("")},je=function(e){return e.key===J},de=function(){return q(0)},be=function(){var e=Object(x.a)(m.a.mark((function e(t){return m.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:q(0);case 1:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}();Object(r.useEffect)((function(){O(t).then((function(e){M(!1),b(e.map((function(e){return Object(g.a)(Object(g.a)({},e),{},{key:e.id})})))})).catch((function(e){return E.b.error(e)}))}),[t]);var Oe=[].concat(Object(y.a)(i.map((function(e){return Object(g.a)(Object(g.a)({},e),(t=e.dataIndex,{filterDropdown:function(e){var n=e.setSelectedKeys,c=e.selectedKeys,r=e.confirm,i=e.clearFilters;return Object(G.jsxs)("div",{style:{padding:8},children:[Object(G.jsx)(v.a,{placeholder:"Search ".concat(t),value:c[0],onChange:function(e){return n(e.target.value?[e.target.value]:[])},onPressEnter:function(){return le(c,r,t)},style:{marginBottom:8,display:"block"}}),Object(G.jsxs)(C.b,{children:[Object(G.jsx)(k.a,{type:"primary",icon:Object(G.jsx)(L.a,{}),onClick:function(){return le(c,r,t)},size:"small",style:{width:90},children:"Search"}),Object(G.jsx)(k.a,{onClick:function(){return ue(i)},size:"small",style:{width:90},children:"Reset"})]})]})},filterIcon:function(e){return Object(G.jsx)(L.a,{style:{color:e?"#1890ff":void 0}})},onFilter:function(e,n){return n[t]?n[t].toString().toLowerCase().includes(e.toLowerCase()):""},render:function(e){var n;return oe===t?Object(G.jsx)(B.a,{highlightStyle:{backgroundColor:"#ffc069",padding:0},searchWords:[ce],autoEscape:!0,textToHighlight:null!==(n=null===e||void 0===e?void 0:e.toString())&&void 0!==n?n:""}):e}}));var t}))),[{title:"ACTIONS",dataIndex:"actions",render:function(e,t){return je(t)?Object(G.jsxs)("span",{children:[Object(G.jsx)("a",{onClick:function(){return be(t.key)},style:{marginRight:8},children:"Save"}),Object(G.jsx)(I.a,{title:"Sure to cancel?",onConfirm:de,children:Object(G.jsx)("a",{children:"Cancel"})})]}):Object(G.jsx)(N.a.Link,{disabled:0!==J,onClick:function(){o.setFieldsValue(t),q(t.key)},children:"Edit"})}}]).map((function(e){return e.editable?Object(g.a)(Object(g.a)({},e),{},{onCell:function(t){return{record:t,inputType:"age"===e.dataIndex?"number":"text",dataIndex:e.dataIndex,title:e.title,editing:je(t)}}}):e})),he=[Object(G.jsx)(w.a,{trigger:"click",content:Object(G.jsxs)("div",{children:[K(n,(function(e){h(t,e).then((function(t){return b([].concat(Object(y.a)(d),[Object(g.a)(Object(g.a)({},e),{},{id:t.id})]))})).catch((function(e){return E.b.error(e)}))}),c),Object(G.jsx)(k.a,{shape:"round",icon:Object(G.jsx)(F.a,{}),size:"small",onClick:function(){return ee(!1)}})]}),visible:$,children:Object(G.jsx)(k.a,{type:"primary",icon:Object(G.jsx)(A.a,{}),ghost:!0,onClick:function(){ee(!0)},children:"Add record"})}),Object(G.jsx)(k.a,{icon:Object(G.jsx)(P.a,{}),ghost:!0,danger:!0,onClick:function(){0===Y.length?E.b.warning("Nothing is selected"):Promise.all(Y.map((function(e){return f(t,e)}))).then((function(){b(d.filter((function(e){return!Y.includes(e.id)}))),E.b.success("All entities was deleted")}))},children:"Remove"})];return Object(G.jsxs)(l.a,{className:"site-layout",children:[Object(G.jsx)(R.Header,{children:Object(G.jsx)(C.b,{children:he})}),Object(G.jsx)(R.Content,{children:Object(G.jsx)(T.a,{columns:Oe,rowSelection:{selectedRowKeys:Y,onChange:function(e){return Q(e)},selections:[T.a.SELECTION_ALL,T.a.SELECTION_INVERT,T.a.SELECTION_NONE]},dataSource:d,loading:W,components:{body:{cell:z}},pagination:{onChange:de,position:["bottomCenter"]}})})]})};J.defaultProps={enumFields:new Map};var q=J,U=function(){var e=Object(G.jsx)(q,{entity:c.PERSONS,template:{id:"number",name:"string",height:"number",weight:"number",passportID:"string",hairColor:"string"},enumFields:(new Map).set("hairColor",["GREEN","YELLOW","BROWN"])}),t=Object(G.jsx)(q,{entity:c.DRAGONS,template:{id:"number",name:"string",creationDate:"string",age:"number",wingspan:"number",color:"string",type:"string",killer_id:"number"},enumFields:(new Map).set("color",["GREEN","YELLOW","BROWN"]).set("type",["WATER","AIR","FIRE","UNKNOWN"])}),n=Object(r.useState)(e),i=Object(s.a)(n,2),a=i[0],o=i[1],b=Object(r.useState)(!1),O=Object(s.a)(b,2),h=O[0],f=O[1];return Object(G.jsxs)(l.a,{style:{minHeight:"100vh"},children:[Object(G.jsx)(l.a.Sider,{collapsible:!0,collapsed:h,onCollapse:function(){return f(!h)},children:Object(G.jsxs)(u.a,{defaultSelectedKeys:["1"],mode:"inline",children:[Object(G.jsx)(u.a.Item,{icon:Object(G.jsx)(j.a,{}),onClick:function(){return o(e)},children:"Persons"}),Object(G.jsx)(u.a.Item,{icon:Object(G.jsx)(d.a,{}),onClick:function(){return o(t)},children:"Dragons"})]})}),Object(G.jsxs)(l.a,{className:"site-layout",children:[Object(G.jsx)(l.a.Content,{children:Object(G.jsx)("div",{className:"site-layout-background",style:{minHeight:360},children:a})}),Object(G.jsx)(l.a.Footer,{style:{textAlign:"center"},children:Object(G.jsx)("a",{href:"https://se.ifmo.ru/~s265570/cv/",children:"Kulbako Artemy 2021"})})]})]})};o.a.render(Object(G.jsx)(i.a.StrictMode,{children:Object(G.jsx)(U,{})}),document.getElementById("root"))}},[[331,1,2]]]);
//# sourceMappingURL=main.516a6771.chunk.js.map