(this.webpackJsonpfront=this.webpackJsonpfront||[]).push([[0],{186:function(e,t,n){},187:function(e,t,n){},327:function(e,t,n){"use strict";n.r(t);var c,i=n(0),r=n.n(i),a=n(26),o=n.n(a),s=(n(186),n(57)),u=(n(187),n(329)),l=n(72),d=n(338),j=n(339),b=function(e){return e.json()};!function(e){e.PERSONS="http://localhost:8080/back-1.0-ULTIMATE/api/persons/",e.DRAGONS="http://localhost:8080/back-1.0-ULTIMATE/api/dragons/"}(c||(c={}));var O=function(e){return fetch(e,{method:"GET"}).then(b)},h=function(e,t){return fetch("".concat(e).concat(t),{method:"DELETE"}).then(b)},f=n(174),m=n(136),x=n.n(m),p=n(165),g=n(73),y=n(179),S=n(331),k=n(333),C=n(334),I=n(335),E=n(332),v=n(336),N=n(46),T=n(175),w=n(330),A=n(138),L=n(337),R=n(16),D=["editing","dataIndex","title","inputType","record","index","children"],P=function(e){var t=e.editing,n=e.dataIndex,c=e.title,i=e.inputType,r=(e.record,e.index,e.children),a=Object(y.a)(e,D);return Object(R.jsx)("td",Object(g.a)(Object(g.a)({},a),{},{children:t?Object(R.jsx)(S.a.Item,{name:n,style:{margin:0},rules:[{required:!0,message:"Please Input ".concat(c,"!")}],children:"number"===i?Object(R.jsx)(k.a,{}):Object(R.jsx)(C.a,{})}):r}))},_=function(e){var t=e.entity,n=e.columns,c=S.a.useForm(),r=Object(s.a)(c,1)[0],a=Object(i.useState)([]),o=Object(s.a)(a,2),l=o[0],d=o[1],j=Object(i.useState)(!0),b=Object(s.a)(j,2),m=b[0],y=b[1],k=Object(i.useState)(0),C=Object(s.a)(k,2),D=C[0],_=C[1],F=Object(i.useState)([]),G=Object(s.a)(F,2),H=G[0],K=G[1],M=function(e){return e.key===D},U=function(){return _(0)},J=function(){var e=Object(p.a)(x.a.mark((function e(t){return x.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:_(0);case 1:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}();Object(i.useEffect)((function(){O(t).then((function(e){y(!1),d(e.map((function(e){return Object(g.a)(Object(g.a)({},e),{},{key:e.id})})))}))}),[t]);var V=[].concat(Object(f.a)(n),[{title:"ACTIONS",dataIndex:"actions",render:function(e,t){return M(t)?Object(R.jsxs)("span",{children:[Object(R.jsx)("a",{onClick:function(){return J(t.key)},style:{marginRight:8},children:"Save"}),Object(R.jsx)(I.a,{title:"Sure to cancel?",onConfirm:U,children:Object(R.jsx)("a",{children:"Cancel"})})]}):Object(R.jsx)(E.a.Link,{disabled:0!==D,onClick:function(){r.setFieldsValue(t),_(t.key)},children:"Edit"})}}]).map((function(e){return e.editable?Object(g.a)(Object(g.a)({},e),{},{onCell:function(t){return{record:t,inputType:"age"===e.dataIndex?"number":"text",dataIndex:e.dataIndex,title:e.title,editing:M(t)}}}):e}));return Object(R.jsxs)(u.a,{className:"site-layout",children:[Object(R.jsx)(A.Header,{children:Object(R.jsx)(v.b,{size:"middle",children:Object(R.jsx)(N.a,{icon:Object(R.jsx)(L.a,{}),ghost:!0,danger:!0,onClick:function(){0===H.length?T.b.warning("Nothing is selected"):Promise.all(H.map((function(e){return h(t,e)}))).then((function(){d(l.filter((function(e){return!H.includes(e.id)}))),T.b.success("All entities was deleted")}))},children:"Remove"})})}),Object(R.jsx)(A.Content,{children:Object(R.jsx)(w.a,{columns:V,rowSelection:{selectedRowKeys:H,onChange:function(e){return K(e)},selections:[w.a.SELECTION_ALL,w.a.SELECTION_INVERT,w.a.SELECTION_NONE]},dataSource:l,loading:m,components:{body:{cell:P}},pagination:{onChange:U,position:["bottomCenter"]}})})]})},F=function(e){return Object.keys(e).map((function(t){return{title:t.toUpperCase(),key:t,dataIndex:t,editable:"id"!==t,inputType:e[t],sorter:function(e,n){var c=e[t],i=n[t],r=function(e){return[c,i].every((function(t){return typeof t===e}))};return r("number")?c-i:r("string")?c.length-i.length:r("boolean")?c&&i:void 0}}})).filter((function(e){return void 0!==e&&null!==e}))},G=function(){var e=Object(R.jsx)(_,{entity:c.PERSONS,columns:F({id:"number",name:"string",height:"number",weight:"number",passportID:"string",hairColor:"string"})}),t=Object(R.jsx)(_,{entity:c.DRAGONS,columns:F({id:"number",name:"string",creationDate:"string",age:"number",wingspan:"number",color:"string",type:"string",killer_id:"number"})}),n=Object(i.useState)(e),r=Object(s.a)(n,2),a=r[0],o=r[1],b=Object(i.useState)(!1),O=Object(s.a)(b,2),h=O[0],f=O[1];return Object(R.jsxs)(u.a,{style:{minHeight:"100vh"},children:[Object(R.jsx)(u.a.Sider,{collapsible:!0,collapsed:h,onCollapse:function(){return f(!h)},children:Object(R.jsxs)(l.a,{defaultSelectedKeys:["1"],mode:"inline",children:[Object(R.jsx)(l.a.Item,{icon:Object(R.jsx)(d.a,{}),onClick:function(){return o(e)},children:"Persons"}),Object(R.jsx)(l.a.Item,{icon:Object(R.jsx)(j.a,{}),onClick:function(){return o(t)},children:"Dragons"})]})}),Object(R.jsxs)(u.a,{className:"site-layout",children:[Object(R.jsx)(u.a.Content,{children:Object(R.jsx)("div",{className:"site-layout-background",style:{minHeight:360},children:a})}),Object(R.jsx)(u.a.Footer,{style:{textAlign:"center"},children:Object(R.jsx)("a",{href:"https://se.ifmo.ru/~s265570/cv/",children:"Kulbako Artemy 2021"})})]})]})};o.a.render(Object(R.jsx)(r.a.StrictMode,{children:Object(R.jsx)(G,{})}),document.getElementById("root"))}},[[327,1,2]]]);
//# sourceMappingURL=main.e4316381.chunk.js.map