import ReactMarkDown from "react-markdown";


function AboutUsPara() {
  const markdown = `
  # About us

## How we got here

As a group of aspiring software engineers, we started this project in bold hopes
of recreating a more personalized version of the renowned SAP enterprise resource 
planning software. 

### Team Members

- Amine
- Amneet
- Ethan
- Justin
- Nafisa
- Ruo Chen
- Sean
- Tashfia
- Yasaman

## The importance of our work

Enterprise Resource Planning plays a prominent role in the modern business realm
, proving substantial for large-scale organizations seeking a dedicated platform 
to conveniently manage different business functions with minimal paper usage and
manpower consumption. The need for increasingly sophisticated resource planning 
applications is evergrowing, with users demanding more fresh features to boost 
secure and agile dataprocessing functions for maximum business yields. This 
leaves little room for errors, where slight miscalculations or faults may lead 
to major profit losses which in some cases may prove difficult to financially 
recover from. Thus, it is crucial that a newly developed Enterprise Resource 
planning application should not only meet business requirements and 
specifications, but should also aim to provide services with seamless precision.

## Future Plans

While this application uses a bicycle manufacturing company as a fictional 
scenario in order to facilitate standard system requirements, a similar design
can be applied to a wide range of businesses as long as standard phase-driven
processes such as planning, production, inventory management are involved.

Thus, a more generalized version of the GODZILLA application may be developed
in the future to fit a more wide range of buisnesses , including custom features
such as chart drawing,3D visuals , and much more. 
  `;
  return (
    <div>
      <ReactMarkDown source={markdown} />
    </div>
  );
}
export default AboutUsPara;