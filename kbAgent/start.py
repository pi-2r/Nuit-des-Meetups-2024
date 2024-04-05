from flask import  Flask, request
import InvokeAgent as agent
import json

app = Flask(__name__)
@app.route('/')
def hello_geek():
    return '<h1>[Nuit des Meetups] Mettons un peu d’IA Générative dans un bot classique 🤖🚀</h2>'


@app.route('/agent',methods=['POST'])
def callAgent():
    print("Request received")
    print(request)
    request_data = request.get_json()
    question = request_data['question']
    print("Question -> ", question)
    event = {
        "sessionId": "MYSESSION",
        "question": question
    }
    response = agent.lambda_handler(event, None)

    try:
        # Parse the JSON string
        if response and 'body' in response and response['body']:
            response_data = json.loads(response['body'])
            print(response_data['trace_data'])
            return response_data['trace_data']
        else:
            print("Invalid or empty response received")
    except json.JSONDecodeError as e:
        print("JSON decoding error:", e)
        response_data = None

    try:
        # Extract the response and trace data
        all_data = format_response(response_data['response'])
        the_response = response_data['trace_data']
    except:
        all_data = "..."
        response_data = "Apologies, but an error occurred. Please rerun the application"

    return all_data


if __name__ == "__main__":
    app.run(debug=True)
