#version 150

vec4 seasonColor(vec4 start_color, float Season, vec3 light) {
	vec4 color = vec4(start_color.r, start_color.g, start_color.b, start_color.a);

	//0 - 0.125 = end of winter
	//0.125 - 0.375 = spring
	//0.375 - 0.625 = summer
	//0.625 - 0.875 = fall
	//0.875 - 1 = start of winter
	
	vec4 s_color = vec4(1, 1, 1, 0);
	if (Season < 0.125) {
		s_color.r = (start_color.r + 1) / 2.0f;
		s_color.g = (start_color.r + 1) / 2.0f;
		s_color.b = (start_color.r + 1) / 2.0f;
		s_color.a = 1.0f - (Season / 0.125f);
	}
	if (Season > 0.625) {
		float R = 191.0f / 255.0f;
		float G = 167.0f / 255.0f;
		float B = 112.0f / 255.0f;
		if (Season > 0.875) {
			float MIX = (Season - 0.875f) / 0.25f;
			s_color.a = 1.0f;
			
			s_color.r = (mix(start_color.r * R, (start_color.r * R + 1) / 2.0f, MIX));
			s_color.g = (mix(start_color.r * G, (start_color.r * G + 1) / 2.0f, MIX));
			s_color.b = (mix(start_color.r * B, (start_color.r * B + 1) / 2.0f, MIX));
		} else {
			//191, 167, 112
			
			s_color.r = start_color.r * R;
			s_color.g = start_color.r * G;
			s_color.b = start_color.r * B;
			
			float MIX = (Season - 0.625f) / 0.25f;
			
			s_color.a = MIX;
		}
	}
	
	vec3 rgb = color.xyz;
	rgb = mix(rgb, s_color.xyz * light, s_color.w);
	color = vec4(rgb, color.a);
	
	return color;
}